import React, { useState, useEffect } from "react";
import styles from "./PixConfirmationScreen.module.css";
import { IoIosArrowBack } from "react-icons/io";
import { Link, useLocation, useNavigate } from "react-router-dom";
import AlertBanner from "../AlertBanner/AlertBanner";
import { FiFileText } from "react-icons/fi";
import { realizarTransferenciaPix } from "../../api/api";

function PixConfirmationScreen() {
  const location = useLocation();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [showBanner, setShowBanner] = useState(false);
  const descricao = location.state?.descricao || "";
  const valor = location.state?.valor || "0,00";
  const getDataAtualFormatada = () => {
    const data = new Date();
    const dia = String(data.getDate()).padStart(2, "0");
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const ano = data.getFullYear();
    return `${dia}/${mes}/${ano}`;
  };

  const pixData = JSON.parse(localStorage.getItem("consultaIA") || "{}");
  const requestTransaction = JSON.parse(
    localStorage.getItem("requestTransaction") || "{}"
  );

  console.log(pixData.transactionInformation.receiverName);

  const dadosPix = location.state?.dadosPix || {};

  useEffect(() => {
    if (!location.state || !location.state.valor) {
      navigate("/valor");
    }
  }, [location, navigate]);

  useEffect(() => {
    if (pixData?.aiAnalyze?.confidenceScore < 0.7) {
      setShowBanner(true);
    }
  }, []);

  console.log(pixData);

  const handleConfirmar = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await realizarTransferenciaPix(
        requestTransaction.destinationKeyValue,
        requestTransaction.originClientId,
        requestTransaction.amount,
        requestTransaction.description
      );

      const resultadoTransferencia = response.data.body;

      const success = response.data;

      console.log("Ola", success);

      if (success.statusCodeValue === 200) {
        navigate("/sucesso", { state: { dados: dadosPix } });
      } else {
        setError(
          resultadoTransferencia?.message || "Erro ao realizar a transferência."
        );
      }
    } catch (err) {
      console.error("Erro ao transferir pix:", err);
      setError("Erro ao realizar a transferência.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.headerContent}>
          <Link to="/valor" className={styles.backButton}>
            <IoIosArrowBack />
          </Link>
          <h1>Pix</h1>
        </div>
        <h2 className={styles.mainTitle}>Agora, é só confirmar</h2>

        <svg
          className={styles.wave}
          viewBox="0 0 1440 120"
          preserveAspectRatio="none"
          xmlns="http://www.w3.org/2000/svg"
          style={{
            position: "absolute",
            bottom: "-50px",
            left: 0,
            width: "100%",
            height: "70px",
            transform: "rotate(180deg)",
            zIndex: 1,
            pointerEvents: "none",
          }}
        >
          <g transform="translate(70, 0)">
            <path
              d="M0,150 Q720,-150 1440,175 L1440,120 L0,120 Z"
              fill="rgba(227, 60, 79, 0.3)"
            />
          </g>
          <g transform="translate(300, 0)">
            <path
              d="M0,155 Q720,-120 1440,130 L1440,120 L0,120 Z"
              fill="rgba(227, 60, 79, 0.3)"
            />
          </g>
          <path
            d="M0,29  Q360,-10 720,30  Q1080,70 1440,40  L1440,120  L0,120  Z"
            fill="rgba(204, 9, 47, 0.97)"
          />
        </svg>
      </div>

      <div className={styles.content}>
        <div className={styles.transactionDetails}>
          <div className={styles.detailsHeader}>
            <FiFileText size={20} color="#777" style={{ marginRight: 8 }} />
            <span>Dados da transação</span>
          </div>
          <div className={styles.detailItem}>
            <span className={styles.label}>Nome</span>
            <span className={styles.value}>
              {pixData.transactionInformation.receiverName}
            </span>
          </div>
          <div className={styles.detailItem}>
            <span className={styles.label}>Valor</span>
            <span className={styles.value}>R$ {valor}</span>
          </div>
          <div className={styles.detailItem}>
            <span className={styles.label}>Chave Pix</span>
            <span className={`${styles.value} ${styles.blurred}`}>
              {dadosPix.chave || "XXX.XXX.XXX-XX"}
            </span>
          </div>
          <div className={styles.detailItem}>
            <span className={styles.label}>CPF/CNPJ</span>
            <span className={`${styles.value} ${styles.blurred}`}>
              {dadosPix.documento || "***.***.***-**"}
            </span>
          </div>
          <div className={styles.detailItem}>
            <span className={styles.label}>Instituição</span>
            <span className={styles.value}>{dadosPix.instituicao}</span>
          </div>
        </div>

        <div className={styles.options}>
          <label className={styles.checkboxContainer}>
            <input type="checkbox" />
            <span className={styles.checkmark}></span>
            Adicionar aos contatos Pix
          </label>
        </div>

        <div className={styles.descriptionConfirm}>
          <span>Observação:</span>
          <p>{descricao || "Nenhuma observação foi inserida."}</p>
        </div>

        <div className={styles.paymentMethod}>
          <span className={styles.label}>Debitar de</span>
          <span className={styles.value}> Conta-Poupança</span>
        </div>

        <div className={styles.paymentDate}>
          <span className={styles.label}>Data do débito</span>
          <span className={styles.value}> {getDataAtualFormatada()}</span>
        </div>

        {error && <div className={styles.errorMessage}>{error}</div>}

        <button className={styles.confirmButton} onClick={handleConfirmar}>
          Continuar
        </button>
        <Link to="/home" className={styles.cancelButton}>
          Cancelar
        </Link>
      </div>
      {showBanner && (
        <AlertBanner
          message="Alerta: Individuo suspeito detectado!! Aconselhamos que prossiga, apenas se confiar no individuo."
          onClose={() => setShowBanner(false)}
        />
      )}
    </div>
  );
}

export default PixConfirmationScreen;
