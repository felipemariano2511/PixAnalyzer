import pandas as pd
import numpy as np
import joblib
from datetime import datetime
from sklearn.ensemble import HistGradientBoostingRegressor, HistGradientBoostingClassifier
from sklearn.multioutput import MultiOutputClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder, StandardScaler, MultiLabelBinarizer
from sklearn.pipeline import Pipeline
from sklearn.compose import ColumnTransformer
from sklearn.metrics import mean_absolute_error, r2_score, f1_score

df = pd.read_csv('resultado_confiabilidade.csv')

df['dias_desde_birth_date'] = (datetime.now() - pd.to_datetime(df['birth_date'], errors='coerce')).dt.days.fillna(0)
df['dias_desde_timestamp'] = (datetime.now() - pd.to_datetime(df['timestamp'], errors='coerce')).dt.days.fillna(0)
df['dias_desde_registration_date'] = (datetime.now() - pd.to_datetime(df['registration_date'], errors='coerce')).dt.days.fillna(0)

drop_cols = [
    'id',
    'destination_key_value',
    'timestamp',
    'cpf',
    'full_name',
    'birth_date',
    'confidence_score',
    'fraud_reasons'
]

X = df.drop(columns=drop_cols)
y_score = df['confidence_score'].fillna(0.5)

fraud_labels = df['fraud_reasons'].fillna('').apply(lambda x: x.split(',') if x else [])
mlb = MultiLabelBinarizer()
y_fraud = mlb.fit_transform(fraud_labels)

categorical = X.select_dtypes(include='object').columns.tolist()
numerical = X.select_dtypes(include=['int64', 'float64']).columns.tolist()

preprocessor = ColumnTransformer(transformers=[
    ('cat', OneHotEncoder(handle_unknown='ignore', sparse_output=False), categorical),
    ('num', StandardScaler(), numerical)
])

X_train, X_test, y_score_train, y_score_test, y_fraud_train, y_fraud_test = train_test_split(
    X, y_score, y_fraud, test_size=0.2, random_state=42
)

score_model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('regressor', HistGradientBoostingRegressor(random_state=42))
])

fraud_model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('classifier', MultiOutputClassifier(HistGradientBoostingClassifier(random_state=42)))
])

score_model.fit(X_train, y_score_train)
fraud_model.fit(X_train, y_fraud_train)

y_pred_score = score_model.predict(X_test)
print('MAE:', mean_absolute_error(y_score_test, y_pred_score))
print('R²:', r2_score(y_score_test, y_pred_score))

y_pred_fraud = fraud_model.predict(X_test)
print('F1 Micro:', f1_score(y_fraud_test, y_pred_fraud, average='micro'))
print('F1 Macro:', f1_score(y_fraud_test, y_pred_fraud, average='macro'))

joblib.dump(score_model, 'confidence_model.pkl')
joblib.dump(fraud_model, 'fraud_model.pkl')
joblib.dump(mlb, 'fraud_labels_encoder.pkl')
