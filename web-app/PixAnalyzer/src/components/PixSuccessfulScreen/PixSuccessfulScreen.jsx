import React from "react";
import { AiOutlineCheckCircle } from "react-icons/ai";
import { Link } from "react-router-dom";
import { IoIosArrowForward } from "react-icons/io";
import { FaPix } from "react-icons/fa6";
import { IoIosArrowBack } from "react-icons/io";
import { IoHomeOutline } from "react-icons/io5";
import { IoShareOutline } from "react-icons/io5";
import styles from "./PixSuccessfulScreen.module.css";

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
          <div className={styles["money-stack"]}>
            <div className={`${styles.money} ${styles["money-1"]}`}></div>
            <div className={`${styles.money} ${styles["money-2"]}`}></div>
          </div>
          <AiOutlineCheckCircle className={styles["check-icon"]} />
          <div className={styles.coins}>
            <div className={`${styles.coin} ${styles["coin-1"]}`}></div>
            <div className={`${styles.coin} ${styles["coin-2"]}`}></div>
          </div>
        </div>

        <div className={styles["info-area"]}>
          <h2 className={styles["info-title"]}>Pix concluído</h2>
          <p className={styles["info-description"]}>
            Deu tudo certo com a transação de R$ {requestTransaction.amount}.
          </p>
        </div>

        <div className={styles["security-area"]}>
          <p className={styles["security-text"]}>Seu Pix ainda mais</p>
          <div className={styles["protected-area"]}>
            <span className={styles["protected-text"]}>protegido</span>
          </div>
        </div>

        <div className={styles["actions-area"]}>
          <button className={styles["action-button"]}>
            <IoShareOutline className={styles["action-icon"]} />
            <span className={styles["action-label"]}>
              Compartilhar comprovante
            </span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </button>
          <button className={styles["action-button"]}>
            <FaPix className={styles["pix-icon-container"]} />
            <span className={styles["action-label"]}>Fazer outro Pix</span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </button>
          <button className={styles["action-button"]}>
            <IoHomeOutline className={styles["action-icon"]} />
            <span className={styles["action-label"]}>Voltar ao início</span>
            <IoIosArrowForward className={styles["action-arrow"]} />
          </button>
        </div>
      </div>

      <div className={styles["navigation-dots"]}>
        <div className={styles["nav-dot"]}></div>
        <div className={`${styles["nav-dot"]} ${styles.active}`}></div>
        <div className={styles["nav-dot"]}></div>
      </div>
    </div>
  );
}

export default PixSuccessfulScreen;
