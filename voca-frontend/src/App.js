import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import MainPage from "./component/MainPage";
import Signup from "./component/Signup";
import Signin from "./component/Signin";
import ClassSelect from "./component/ClassSelect";

function App() {
  const [data, setData] = useState([]);
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/main" element={<MainPage />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/signin" element={<Signin />}></Route>
          <Route path="/classes" element={<ClassSelect />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
