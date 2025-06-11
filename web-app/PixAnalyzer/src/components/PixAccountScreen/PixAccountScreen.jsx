import React, { useState } from "react";
import styles from "./PixAccountScreen.module.css";
import { Link } from "react-router-dom";
import { FaEye, FaEyeSlash, FaInfoCircle, FaChevronLeft } from "react-icons/fa";

const PixAccountScreen = () => {
  const [selectedAccount, setSelectedAccount] = useState("contaCorrente");
  const [showBalance, setShowBalance] = useState(false);

  const handleAccountSelection = (accountType) => {
    setSelectedAccount(accountType);
  };

  const toggleShowBalance = () => {
    setShowBalance(!showBalance);
  };

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.headerContent}>
          <Link to="/valor" className={styles.backButton}>
            &lt;
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

      <div className={styles.pixBody}>
        <div className={styles.recipientInfoCard}>
          <div className={styles.iconContainer}>S$</div>
          <div className={styles.recipientDetails}>
            <p className={styles.recipientName}>
              Pix para: <span className={styles.bold}>Felipe Mariano</span>
            </p>
            <p className={styles.recipientValue}>
              Valor: <span className={styles.bold}>R$ 0,01</span>
            </p>
          </div>
        </div>

        <section className={styles.sectionAccount}>
          <label htmlFor="accountSelection" className={styles.accountLabel}>
            De onde o valor será debitado?
          </label>

          <div className={styles.showBalanceContainer}>
            <button
              className={styles.showBalanceButton}
              onClick={toggleShowBalance}
            >
              {showBalance ? (
                <>
                  Esconder Saldo <FaEye className={styles.eyeButton} />
                </>
              ) : (
                <>
                  Mostrar Saldo{" "}
                  <FaEyeSlash className={styles.eyeButtonHidden} />
                </>
              )}
            </button>
          </div>

          <div className={styles.accountCard}>
            <label className={styles.accountOption}>
              <input
                type="radio"
                name="account"
                value="contaCorrente"
                checked={selectedAccount === "contaCorrente"}
                onChange={() => handleAccountSelection("contaCorrente")}
              />
              <div className={styles.accountDetails}>
                <p className={styles.bankName}>Banco Bradesco</p>
                <p className={styles.accountNumber}>
                  Ag.: <span className={styles.masked}>****</span> C/C:{" "}
                  <span className={styles.masked}>*****</span>
                </p>
                <p className={styles.accountBalance}>
                  Saldo disponível:{" "}
                  <span
                    className={
                      showBalance ? styles.visibleBalance : styles.maskedBalance
                    }
                  >
                    R$ 1.234,56
                  </span>
                </p>
              </div>
            </label>
          </div>

          <div className={styles.accountCard}>
            <label className={styles.accountOption}>
              <input
                type="radio"
                name="account"
                value="contaPoupanca"
                checked={selectedAccount === "contaPoupanca"}
                onChange={() => handleAccountSelection("contaPoupanca")}
              />
              <div className={styles.accountDetails}>
                <p className={styles.bankName}>Banco Bradesco</p>
                <p className={styles.accountNumber}>
                  Ag.: <span className={styles.masked}>****</span> C/Poup.:{" "}
                  <span className={styles.masked}>*****</span>
                </p>
                <p className={styles.accountBalance}>
                  Saldo disponível:{" "}
                  <span
                    className={
                      showBalance ? styles.visibleBalance : styles.maskedBalance
                    }
                  >
                    R$ 789,01
                  </span>
                </p>
              </div>
            </label>
          </div>

          <div className={styles.otherAccountCard}>
            <label className={styles.otherAccountOption}>
              <input
                type="radio"
                name="account"
                value="outraInstituicao"
                checked={selectedAccount === "outraInstituicao"}
                onChange={() => handleAccountSelection("outraInstituicao")}
              />
              <div className={styles.otherAccountDetails}>
                <p className={styles.otherAccountTitle}>
                  Conta de outra instituição
                </p>
                <p className={styles.otherAccountDescription}>
                  Você faz a transação por aqui, mas debita de outra
                  instituição.
                </p>
              </div>
            </label>
          </div>
        </section>
      </div>

      <div className={styles.pixFooter}>
        <Link to="/confirmar" className={styles.continueButton}>
          Continuar
        </Link>

        <Link to="/home" className={styles.cancelButton}>
          Cancelar
        </Link>
      </div>
    </div>
  );
};

export default PixAccountScreen;
