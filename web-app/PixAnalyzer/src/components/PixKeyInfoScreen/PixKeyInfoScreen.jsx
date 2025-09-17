import React, { useEffect, useState } from "react";
import styles from "./PixKeyInfoScreen.module.css";
import { IoIosArrowBack } from "react-icons/io";
import { FiCopy } from "react-icons/fi";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

function UserCard({ user }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    if (!user.destinationKeyValue) return;
    navigator.clipboard
      .writeText(user.destinationKeyValue)
      .then(() => {
        setCopied(true);
        setTimeout(() => setCopied(false), 1500);
      })
      .catch(() => alert("Falha ao copiar"));
  };

  let backgroundColor = "";
  let borderColor = "";

  if (user.confidenceScore > 0.7) {
    backgroundColor = "#d1fae5";
    borderColor = "#10b981";
  } else if (user.confidenceScore > 0.4) {
    backgroundColor = "#fef3c7";
    borderColor = "#f59e0b";
  } else {
    backgroundColor = "#fee2e2";
    borderColor = "#ef4444";
  }

  return (
    <div
      className={styles.userCard}
      style={{
        backgroundColor,
        borderLeft: `6px solid ${borderColor}`,
        padding: "1rem",
        borderRadius: "8px",
        marginBottom: "1rem",
      }}
    >
      <strong>Nome:</strong> {user.ownerName || "—"}
      <br />
      <strong>Chave Pix:</strong> {user.destinationKeyValue || "—"}{" "}
      
      {user.destinationKeyValue?.trim() && (
        <span
          className={styles.clipboardIcon}
          onClick={handleCopy}
          style={{ cursor: "pointer", verticalAlign: "middle" }}
          title="Copiar chave"
        >
          <FiCopy size={20} color={copied ? "green" : "gray"} />
        </span>
      )}
      <br />
      <strong>Instituição:</strong> {user.institution || "—"}
      <br />
      <strong>Valor:</strong> R$ {user.amount?.toFixed(2) || "—"}
      <br />
      <strong>Score:</strong> {user.confidenceScore}
    </div>
  );
}

export default function PixKeyInfoScreen() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  async function buscarDados() {
    try {
      const response = await axios.get(
        "https://localhost/api/pix-analyzer/transferencia/pix/info-chave-pix"
      );
      return response;
    } catch (error) {
      console.error("Erro:", error);
    }
  }

  useEffect(() => {
    async function loadUsers() {
      setLoading(true);
      try {
        const res = await buscarDados();
        if (!res) throw new Error("Resposta vazia");

        const dados = res.data.body;

        if (Array.isArray(dados) && dados.length) setUsers(dados);
        else setError("Nenhum usuário encontrado.");
      } catch (error) {
        console.error(error);
        setError("Erro ao buscar usuários.");
      } finally {
        setLoading(false);
      }
    }
    loadUsers();
  }, []);

  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.pixGradient}>
          <span className={styles.backArrow} onClick={() => navigate('/input')}>
            <IoIosArrowBack />
          </span>
          <h1 className={styles.projectName}>Pix Analyzer</h1>

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
      </div>

      <div className={styles.pixBody}>
        {loading && <p className={styles.loading}>Carregando...</p>}
        {error && <p className={styles.errorText}>{error}</p>}
        {!loading && !error && users.length > 0 && (
          <>
            {/* Legenda explicando as cores */}
            <div className={styles.legendContainer}>
              <h2>Legenda de Confiabilidade</h2>
              <div className={styles.legendItems}>
                <div className={styles.legendItem}>
                  <span
                    className={styles.colorBox}
                    style={{ backgroundColor: "#d1fae5", borderColor: "#10b981" }}
                  ></span>
                  <span>Muito confiável (score &gt; 0.7)</span>
                </div>
                <div className={styles.legendItem}>
                  <span
                    className={styles.colorBox}
                    style={{ backgroundColor: "#fef3c7", borderColor: "#f59e0b" }}
                  ></span>
                  <span>Confiabilidade média (0.4 &lt; score ≤ 0.7)</span>
                </div>
                <div className={styles.legendItem}>
                  <span
                    className={styles.colorBox}
                    style={{ backgroundColor: "#fee2e2", borderColor: "#ef4444" }}
                  ></span>
                  <span>Não confiável (score ≤ 0.4)</span>
                </div>
              </div>
            </div>

            {/* Grid dos usuários */}
            <div className={styles.userGrid}>
              {users.slice().reverse().map((user, i) => (
                <UserCard key={i} user={user} />
              ))}
            </div>

            <div className={styles.footerButtonWrapper}>
  <button
    className={styles.confirmButton}
    onClick={() => navigate("/input")} // ajuste a rota conforme necessário
  >
    Continuar
  </button>
</div>

          </>
        )}
      </div>
    </div>
  );
}
