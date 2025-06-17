import React from "react";
import { FiAlertTriangle } from "react-icons/fi";
import styles from "./AlertBanner.module.css";

function AlertBanner({ message, onClose }) {
  return (
    <div className={styles.overlay}>
      <div className={styles.banner}>
        <span>{message}</span>
        <button onClick={onClose}>Fechar</button>
      </div>
    </div>
  );
}

export default AlertBanner;
