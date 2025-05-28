import React, { useState, useEffect } from "react";
import styles from "./PixConfirmationScreen.module.css";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { FiFileText } from "react-icons/fi";

function PixConfirmationScreen() {
  const location = useLocation();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const pixData = JSON.parse(localStorage.getItem("consultaIA") || "{}");

  const dadosPix = location.state?.dadosPix || {};
  const valor = location.state?.valor || "0,00";
  const valorNumerico = parseFloat(valor.replace(".", "").replace(",", "."));

  useEffect(() => {
    if (pixData?.aiAnalyze?.confidenceScore < 0.7) {
      alert("Avaliação de confiança baixa. Por favor, só realize a transferência se realmente confiar no destinatário.");
    }
  }, []);

  const handleConfirmar = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await consultarAvaliacaoIA(
        pixData.destinationKeyValue,
        pixData.originClientId,
        valorNumerico,
        "teste"
      );

      const dados = response.data.body;

      if (dados) {
        console.log("Dados da chave:", dados);
        navigate("/home", { state: { dados } });
      } else {
        alert("Chave Pix inválida ou não encontrada.");
      }
    } catch (err) {
      console.error("Erro ao buscar chave:", err);
      alert("Erro ao transferir pix.");
    }
  };

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.headerContent}>
          <Link to="/conta" className={styles.backButton}>&lt;</Link>
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
            <span className={styles.value}>{dadosPix.nome}</span>
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

        <div className={styles.paymentMethod}>
          <span className={styles.label}>Debitar de</span>
          <span className={styles.value}> Conta-Poupança</span>
        </div>

        <div className={styles.paymentDate}>
          <span className={styles.label}>Data do débito</span>
          <span className={styles.value}> 30/04/2025 - hoje</span>
        </div>

        {error && <div className={styles.errorMessage}>{error}</div>}

        <button className={styles.continueButton} onClick={handleContinue}>
               Continuar
        </button>
        <button className={styles.cancelButton}>Cancelar</button>
      </div>
    </div>
  );
}

export default PixConfirmationScreen;
