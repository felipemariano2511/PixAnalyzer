import styles from "./PixTransactionScreen.module.css";
import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { FaEye, FaEyeSlash, FaInfoCircle } from "react-icons/fa";
import { postBuscarDadosChavePix } from "../../api/clientApi";

function PixTransactionScreen() {
  const location = useLocation();
  const { destinationKeyValue, originClientId } = location.state || {};

  const [amount, setAmount] = useState("");
  const [showBalance, setShowBalance] = useState(false);
  const [apiData, setApiData] = useState(null);

  const toggleBalanceVisibility = () => setShowBalance(!showBalance);

  useEffect(() => {
    if (!destinationKeyValue || !originClientId) return;

    postBuscarDadosChavePix({ destinationKeyValue, originClientId })
      .then((res) => {
        setApiData(res.data.body);
      })
      .catch((err) => {
        console.error("Erro ao buscar dados da chave Pix:", err);
      });
  }, [destinationKeyValue, originClientId]);

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.headerContent}>
          <Link to="/" className={styles.backButton}>
            <span className={styles.backArrow}>←</span>
          </Link>
          <h1>Pix</h1>
          <div className={styles.infoBar}>
            <FaInfoCircle className={styles.infoIcon} />
            <Link to="#" className={styles.infoLink}>
              Horários, limites e outras informações
            </Link>
          </div>
        </div>

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
            height: "100px",
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

      <div className={styles.pixBody}>
        <div className={styles.pixBodyInner}>
          <div className={styles.recipientInfoCard}>
            <div className={styles.iconContainer}>S$</div>
            <div className={styles.recipientDetails}>
              <p className={styles.recipientName}>
                Pix para:{" "}
                <span className={styles.bold}>
                  {apiData?.receiverName || "Carregando..."}
                </span>
              </p>
              <p className={styles.recipientId}>
                CPF/CNPJ:{" "}
                <span className={styles.blurred}>
                  {apiData?.taxIdNumber || "***.***.***-**"}
                </span>
              </p>
              <p className={styles.recipientInstitution}>
                Instituição:{" "}
                <span className={styles.blurred}>
                  {apiData?.destinationBank || "***"}
                </span>
              </p>
            </div>
          </div>

          <section className={styles.amountSection}>
            <label htmlFor="amountInput" className={styles.amountLabel}>
              Escolha o valor
            </label>
            <div className={styles.secondaryLabel}>Valor</div>
            <div className={styles.amountInputWrapper}>
              <span className={styles.currencyPrefix}>R$</span>
              <input
                type="number"
                id="amountInput"
                className={styles.amountInput}
                placeholder="0,00"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
              />
            </div>
            <div className={styles.quickAmountScroll}>
              <div className={styles.quickAmountButtons}>
                <button
                  type="button"
                  onClick={() => setAmount((Number(amount) || 0) + 1)}
                >
                  + R$ 1
                </button>
                <button
                  type="button"
                  onClick={() => setAmount((Number(amount) || 0) + 10)}
                >
                  + R$ 10
                </button>
                <button
                  type="button"
                  onClick={() => setAmount((Number(amount) || 0) + 50)}
                >
                  + R$ 50
                </button>
                <button
                  type="button"
                  onClick={() => setAmount((Number(amount) || 0) + 100)}
                >
                  + R$ 100
                </button>
              </div>
            </div>
          </section>

          <section className={styles.balanceSection}>
            <div className={styles.balanceLabel}>Saldo disponível:</div>
            <div className={styles.balanceValue}>
              <span
                className={`${styles.balanceText} ${
                  !showBalance ? styles.hiddenBalance : ""
                }`}
              >
                R$ {showBalance && apiData ? apiData.balance.toFixed(2) : "*****"}
              </span>
              <button
                onClick={toggleBalanceVisibility}
                className={styles.eyeButton}
              >
                {showBalance ? <FaEye /> : <FaEyeSlash />}
              </button>
            </div>
          </section>

          <section className={styles.scheduleSection}>
            <div className={styles.scheduleInfo}>
              <div className={styles.scheduleLabel}>Para quando?</div>
              <div className={styles.scheduleDate}>30/04/2025</div>
            </div>
            <div className={styles.repeatWrapper}>
              <button className={styles.repeatButton}>Repetir</button>
              <span className={styles.calendarEmoji}>📅</span>
            </div>
          </section>

          <div className={styles.actionButtons}>
            <Link
              to="/conta"
              className={`${styles.continueButton} ${
                Number(amount) <= 0 ? styles.disabled : ""
              }`}
              onClick={(e) => {
                if (Number(amount) <= 0) e.preventDefault();
              }}
            >
              Continuar
            </Link>

            <button className={styles.cancelButton}>Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PixTransactionScreen;
