import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import MainPage from "./MainPage";

function App() {
  const [data, setData] = useState([]);
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/main" element={<MainPage />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
