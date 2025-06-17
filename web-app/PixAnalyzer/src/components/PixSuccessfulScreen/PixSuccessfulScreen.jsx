import React from "react";
import { Link } from "react-router-dom";
import { IoIosArrowForward, IoIosArrowBack } from "react-icons/io";
import { IoHomeOutline, IoShareOutline } from "react-icons/io5";
import { FaPix } from "react-icons/fa6";
import { FcLock } from "react-icons/fc";
import styles from "./PixSuccessfulScreen.module.css";
import pixSuccessImage from "../../../public/money.png";

function PixSuccessfulScreen() {
  const requestTransaction = JSON.parse(
    localStorage.getItem("requestTransaction") || "[]"
  );

  return (
    <div className={styles["pix-container"]}>
      <div className={styles["pix-header"]}>
        <Link to="/home" className={styles["header-back-icon"]}>
          <IoIosArrowBack />
        </Link>
        <span className={styles["header-title"]}>Pix</span>
      </div>

      <div className={styles["pix-content"]}>
        <div className={styles["check-area"]}>
          <img
            src={pixSuccessImage}
            alt="Pix concluído"
            className={styles["pix-success-image"]}
          />
        </div>

        <div className={styles["info-area"]}>
          <h2 className={styles["info-title"]}>Pix concluído</h2>
          <p className={styles["info-description"]}>
            Deu tudo certo com a transação de <br /> R$ {Number(requestTransaction.amount).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}.
          </p>
        </div>

        <div className={styles["security-box"]}>
          <div className={styles["color-bar"]}></div>
          <div className={styles["security-content"]}>
            <span className={styles["security-text"]}>
              Seu Pix ainda mais protegido
            </span>
            <FcLock className={styles["security-icon"]} />
          </div>
        </div>

        <div className={styles["actions-area"]}>
          <Link to="/home" className={styles["action-button"]}>
            <IoShareOutline className={styles["action-icon"]} />
            <span className={styles["action-label"]}>
              Compartilhar comprovante
            </span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </Link>
          <Link to="/home" className={styles["action-button"]}>
            <FaPix className={styles["pix-icon-container"]} />
            <span className={styles["action-label"]}>Fazer outro Pix</span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </Link>
          <Link to="/home" className={styles["action-button"]}>
            <IoHomeOutline className={styles["action-icon"]} />
            <span className={styles["action-label"]}>Voltar ao início</span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </Link>
        </div>
      </div>
    </div>
  );
}

export default PixSuccessfulScreen;
