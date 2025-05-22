import pandas as pd
import numpy as np
import joblib
from sklearn.ensemble import RandomForestRegressor
from sklearn.multioutput import MultiOutputClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder, StandardScaler, MultiLabelBinarizer
from sklearn.pipeline import Pipeline
from sklearn.compose import ColumnTransformer

df = pd.read_csv('resultado_confiabilidade.csv')

fraud_labels = df['fraud_reasons'].fillna('').apply(lambda x: x.split(',') if x else [])
mlb = MultiLabelBinarizer()
y_fraud = mlb.fit_transform(fraud_labels)
df['confidence_score'] = df['confidence_score'].fillna(0.5)

X = df.drop(columns=['confidence_score', 'fraud_reasons'])
y_score = df['confidence_score']

categorical = X.select_dtypes(include='object').columns.tolist()
numerical = X.select_dtypes(include=['int64', 'float64']).columns.tolist()

preprocessor = ColumnTransformer(transformers=[
    ('cat', OneHotEncoder(handle_unknown='ignore'), categorical),
    ('num', StandardScaler(), numerical)
])

X_train, X_test, y_score_train, y_score_test, y_fraud_train, y_fraud_test = train_test_split(
    X, y_score, y_fraud, test_size=0.2, random_state=42
)

score_model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('regressor', RandomForestRegressor(n_estimators=200, random_state=42))
])

fraud_model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('classifier', MultiOutputClassifier(RandomForestRegressor(n_estimators=200, random_state=42)))
])

score_model.fit(X_train, y_score_train)
fraud_model.fit(X_train, y_fraud_train)

joblib.dump(score_model, 'confidence_model.pkl')
joblib.dump(fraud_model, 'fraud_model.pkl')
joblib.dump(mlb, 'fraud_labels_encoder.pkl')
