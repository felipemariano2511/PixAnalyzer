import styles from "./PixInputScreen.module.css";
import { FiCopy } from "react-icons/fi";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { buscarDadosDaChavePix } from "../../api/api";

function PixInputScreen() {
  const [pixKey, setPixKey] = useState("");
  const [copied, setCopied] = useState(false);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  
  const handleContinue = async () => {
    const chave = pixKey.trim();
    if (!chave) return;
    
    setLoading(true);
    setError("");
    
    
    
    try {
      const response = await buscarDadosDaChavePix(chave, 3);
      const dados = response.data.body;

      
      console.log(response)
      if (dados && dados.originClientName) {
        console.log("Dados da chave:", dados);
        localStorage.setItem('dados', JSON.stringify(dados));
        navigate("/valor", { state: { dados } });

      } else {
        setError("Chave Pix inválida ou não encontrada.");
      }
    } catch (err) {
      console.error("Erro ao buscar chave:", err);
      setError("Erro ao consultar a chave. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  const handleCopy = () => {
    if (pixKey.trim()) {
      navigator.clipboard.writeText(pixKey).then(() => {
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
      });
    }
  };


  return (
    <div className={styles.pixContainer}>
      <div className={styles.pixHeader}>
        <div className={styles.pixGradient}>
          <span className={styles.backArrow}>&lt;</span>
          <h1>Como você quer transferir?</h1>

          <svg
            className="wave"
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
        <div className={styles.floatingLabelInput}>
          <input
            type="text"
            id="pixKey"
            value={pixKey}
            onChange={(e) => setPixKey(e.target.value)}
            required
            placeholder=" "
            autoComplete="on"
            className={pixKey ? "has-value" : ""}
          />
          <label htmlFor="pixKey">Digitar ou colar nome/chave</label>
          {pixKey.trim() && (
            <span
              className={styles.clipboardIcon}
              onClick={handleCopy}
              style={{ cursor: "pointer" }}
            >
              <FiCopy size={20} color={copied ? "green" : "gray"} />
            </span>
          )}
        </div>

        <p className={styles.pixSubtext}>
          Pode ser o nome do contato ou uma chave Pix
        </p>

        {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
      </div>

      <div className={styles.pixFooter}>


        <button
          className={styles.pixButton}
          onClick={handleContinue}
          disabled={!pixKey.trim() || loading}
        >
          {loading ? "Consultando..." : "Continuar"}
        </button>

        
      </div>
    </div>
  );
}

export default PixInputScreen;
