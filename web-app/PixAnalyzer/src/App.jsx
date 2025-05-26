import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PixInputScreen from "../src/components/PixInputScreen/PixInputScreen";
import PixTransactionScreen from "../src/components/PixTransactionScreen/PixTransactionScreen";
import PixAccountScreen from "../src/components/PixAccountScreen/PixAccountScreen";

function App() {
  return (
    <Router basename="/web-app">
      <Routes>
        <Route path="/home" element={<PixInputScreen />} />
        <Route path="/valor" element={<PixTransactionScreen />} />
        <Route path="/conta" element={<PixAccountScreen />} />
      </Routes>
    </Router>
  );
}

export default App;
