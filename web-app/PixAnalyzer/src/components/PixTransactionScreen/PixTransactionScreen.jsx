import styles from "./PixTransactionScreen.module.css";
import React, { useState } from "react";
import { IoIosArrowBack } from "react-icons/io";
import { Link, useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash, FaInfoCircle } from "react-icons/fa";
import { consultarAvaliacaoIA } from "../../api/api";

function PixTransactionScreen() {
  const [amount, setAmount] = useState("");
  const [showBalance, setShowBalance] = useState(false);
  const navigate = useNavigate();
  const [descricao, setDescricao] = useState("");
  const getDataAtualFormatada = () => {
    const data = new Date();
    const dia = String(data.getDate()).padStart(2, "0");
    const mes = String(data.getMonth() + 1).padStart(2, "0");
    const ano = data.getFullYear();

    return `${dia}/${mes}/${ano}`;
  };

  const pixData = JSON.parse(localStorage.getItem("dados") || "[]");
  const requestTransaction = JSON.parse(
    localStorage.getItem("requestTransaction") || "[]"
  );
  console.log(requestTransaction);

  const handleContinue = async () => {
   const parseValor = (valor) => {
  const cleaned = valor.replace(/\./g, "").replace(",", ".");
  const numero = Number(cleaned);
  return isNaN(numero) ? 0 : numero;
};

const valorNumerico = parseValor(amount);

    if (isNaN(valorNumerico) || valorNumerico <= 0) {
      alert("Informe um valor válido.");
      return;
    }
    requestTransaction.amount = valorNumerico;
    requestTransaction.description = descricao;

    localStorage.setItem(
      "requestTransaction",
      JSON.stringify(requestTransaction)
    );

    try {
      const response = await consultarAvaliacaoIA(
        requestTransaction.destinationKeyValue,
        requestTransaction.originClientId,
        requestTransaction.amount,
        requestTransaction.description
      );

      const dadosIA = response.data.body;

      if (dadosIA) {
        localStorage.setItem("consultaIA", JSON.stringify(dadosIA));

        navigate("/confirmar", {
          state: {
            dadosPix: {
              chave: requestTransaction.destinationKeyValue,
              documento: pixData.taxIdNumber,
              instituicao: pixData.destinationBank,
            },
            valor: requestTransaction.amount,
            descricao: requestTransaction.description,
          },
        });
      } else {
        alert("Chave Pix inválida ou não encontrada.");
      }
    } catch (err) {
      console.error("Erro ao buscar chave:", err);
      alert("Erro ao consultar IA.");
    }
  };

  const toggleBalanceVisibility = () => setShowBalance(!showBalance);

  const formatAmount = (valorAdicionar) => {
  const parseValor = (valor) => {
    const cleaned = valor.replace(/\./g, "").replace(",", ".");
    return parseFloat(cleaned) || 0;
  };

  const valorAtual = parseValor(amount);
  const novoValor = valorAtual + valorAdicionar;

  const formatted = novoValor.toLocaleString("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });

  setAmount(formatted);
};


  const maskTaxId = (value) => {
    const digits = value.replace(/\D/g, "");

    if (digits.length === 11) {
      // CPF: ***.456.789-**
      return `***.${digits.slice(3, 6)}.${digits.slice(6, 9)}-**`;
    } else if (digits.length === 14) {
      // CNPJ: **.***.456/2796-**
      return `**.***.${digits.slice(5, 8)}/${digits.slice(8, 12)}-**`;
    }

    return value;
  };

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.headerContent}>
          <Link to="/input" className={styles.backButton}>
            <IoIosArrowBack />
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
          {pixData ? (
            <div className={styles.recipientInfoCard}>
              <div className={styles.iconContainer}>S$</div>
              <div className={styles.recipientDetails}>
                <p className={styles.recipientName}>
                  Pix para:{" "}
                  <span className={styles.bold}>{pixData.receiverName}</span>
                </p>
                <p className={styles.recipientId}>
                  CPF/CNPJ:{" "}
                  <span className={styles.blurred}>
                    {maskTaxId(pixData.taxIdNumber)}
                  </span>
                </p>
                <p className={styles.recipientInstitution}>
                  Instituição:{" "}
                  <span className={styles.blurred}>
                    {pixData.destinationBank}
                  </span>
                </p>
              </div>
            </div>
          ) : (
            <p>Carregando dados do destinatário...</p>
          )}

          <section className={styles.amountSection}>
            <label htmlFor="amountInput" className={styles.amountLabel}>
              Escolha o valor
            </label>
            <div className={styles.secondaryLabel}>Valor</div>
            <div className={styles.amountInputWrapper}>
              <span className={styles.currencyPrefix}>R$</span>
             <input
                type="text"
                id="amountInput"
                className={styles.amountInput}
                placeholder="0,00"
                value={amount}
                onChange={(e) => {
                  let digits = e.target.value.replace(/\D/g, "");

                  if (!digits) {
                    setAmount("");
                    return;
                  }

                  const numericValue = parseInt(digits, 10);
                  const formatted = (numericValue / 100).toLocaleString('pt-BR', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2,
                  });

                  setAmount(formatted);
                }}
              />
            </div>
            <div className={styles.quickAmountScroll}>
              <div className={styles.quickAmountButtons}>
                <button type="button" onClick={() => formatAmount(1)}>
                  + R$ 1
                </button>
                <button type="button" onClick={() => formatAmount(10)}>
                  + R$ 10
                </button>
                <button type="button" onClick={() => formatAmount(50)}>
                  + R$ 50
                </button>
                <button type="button" onClick={() => formatAmount(100)}>
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
                {showBalance
                  ? `R$ ${Number(pixData.balance).toLocaleString("pt-BR", {
                      minimumFractionDigits: 2,
                      maximumFractionDigits: 2,
                    })}`
                  : ""}
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
              <div className={styles.scheduleLabel}>Pra quando?</div>
              <div className={styles.scheduleDate}>
                {getDataAtualFormatada()}
              </div>
            </div>
            <div className={styles.repeatWrapper}>
              <button className={styles.repeatButton}>Repetir</button>
              <span className={styles.calendarEmoji}>📅</span>
            </div>
          </section>

          <section className={styles.descriptionSection}>
            <label className={styles.descriptionLabel}>
              Descrição (opcional)
            </label>
            <textarea
              className={styles.descriptionInput}
              placeholder="Digite uma observação sobre o Pix"
              value={descricao}
              onChange={(e) => setDescricao(e.target.value)}
            />
          </section>

          <div className={styles.actionButtons}>
            <button className={styles.continueButton} onClick={handleContinue}>
              Continuar
            </button>
            <Link to="/home" className={styles.cancelButton}>
              Cancelar
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PixTransactionScreen;
