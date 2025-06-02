import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import PixInputScreen from "../src/components/PixInputScreen/PixInputScreen";
import PixTransactionScreen from "../src/components/PixTransactionScreen/PixTransactionScreen";
import PixAccountScreen from "../src/components/PixAccountScreen/PixAccountScreen";
import PixConfirmationScreen from "../src/components/PixConfirmationScreen/PixConfirmationScreen";
import PixSuccessfulScreen from "../src/components/PixSuccessfulScreen/PixSuccessfulScreen";

function App() {
  return (
    <Router  basename="/web-app">
      <Routes>
        <Route path="/" element={<Navigate to="/home" replace />} />
        <Route path="/home" element={<PixInputScreen />} />
        <Route path="/valor" element={<PixTransactionScreen />} />
        <Route path="/conta" element={<PixAccountScreen />} />
        <Route path="/confirmar" element={<PixConfirmationScreen />} />
        <Route path="/sucesso" element={<PixSuccessfulScreen />} />
      </Routes>
    </Router>
  );
}

export default App;
