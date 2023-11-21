import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/signin.css";
import useAuth from "../hooks/useAuth";

import axios from "../api/axios";
const LOGIN_URL = "auth/login";

const Signin = () => {
  const { setAuth } = useAuth();
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault(); //prevents reload

    try {
      const response = await axios.post(
        LOGIN_URL,
        JSON.stringify({ username: id, password: password }),
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      console.log(response);
      const accessToken = response?.data?.accessToken;
      //const roles = response?.data.roles;
      setAuth({ id, accessToken }); //save in global context // add roles
      localStorage.setItem("id", id);
      localStorage.setItem("accessToken", accessToken);
      navigate("/classes");
    } catch (err) {
      if (!err.response) {
        console.log("NO SERVER RESPONSE");
      } else if (err.response?.status == 401) {
        console.log("FAILED LOGIN");
      } else {
        console.log("UNEXPECTED ERROR");
      }
    }
    setId("");
    setPassword("");
  };

  return (
    <div id="signin_page_container">
      <div id="signin_wrapper">
        <div id="logo_wrapper">
          <a href="/main" id="logo">
            채점해VOCA
          </a>
        </div>
        <form id="signin_form" onSubmit={handleSubmit}>
          <div className="input_container">
            <input
              className="signin_input"
              type="text"
              placeholder="ID"
              onChange={(e) => setId(e.target.value)}
              value={id}
            ></input>
          </div>

          <div className="input_container">
            <input
              className="signin_input"
              type="password"
              placeholder="PASSWORD"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            ></input>
          </div>

          <div id="button_container">
            <button className="submit_button">로그인</button>
          </div>
        </form>

        <div id="signin_container">
          <a id="signin_a" href="/signup">
            회원가입
          </a>
        </div>
      </div>
    </div>
  );
};

export default Signin;
