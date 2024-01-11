import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/signup.css";
import axios from "../api/axios";

const SIGNUP_URL = "auth/signup";

const Signup = () => {
  var [id, setId] = useState("");
  var [email, setEmail] = useState("");
  var [password, setPassword] = useState("");
  var [password_confirm, setPasswordConfirm] = useState("");
  var [name, setName] = useState("");
  var [role, setRole] = useState("");

  let navigate = useNavigate();

  useEffect(() => {
    var element = document.getElementById("id_error_msg");
    if (id.length == 0) element.innerText = "";
    else if (!validateId(id))
      element.innerText = "아이디는 영어 대소문자 8~12자로 구성되어야합니다.";
    else element.innerText = "";
  }, [id]);
  useEffect(() => {
    var element = document.getElementById("email_error_msg");
    if (email.length == 0) element.innerText = "";
    else if (!validateEmail(email))
      element.innerText = "이메일 형식이 맞지 않습니다.";
    else element.innerText = "";
  }, [email]);
  useEffect(() => {
    var element = document.getElementById("password_error_msg");
    if (password.length == 0) element.innerText = "";
    else if (!validatePassword(password))
      element.innerText = "비밀번호는 영어 대소문자 8~12자로 구성되어야합니다.";
    else element.innerText = "";
  }, [password]);
  useEffect(() => {
    var element = document.getElementById("password_confirm_error_msg");
    if (password_confirm.length == 0) element.innerText = "";
    else if (!validatePasswordConfirm(password_confirm))
      element.innerText = "비밀번호와 일치하지 않습니다.";
    else element.innerText = "";
  }, [password_confirm]);

  const onChangeEmail = (event) => {
    setEmail(event.currentTarget.value);
  };
  const onChangeId = (event) => {
    setId(event.currentTarget.value);
  };
  const onChangePassword = (event) => {
    setPassword(event.currentTarget.value);
  };
  const onChangePasswordConfirm = (event) => {
    setPasswordConfirm(event.currentTarget.value);
  };
  const onChangeName = (event) => {
    setName(event.currentTarget.value);
  };
  const onChangeRole = (event) => {
    setRole(event.currentTarget.value);
  };

  const validateEmail = (email) => {
    var regExp =
      /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return regExp.test(email);
  };
  const validateId = (id) => {
    var regExp = /^[a-zA-z0-9]{8,12}$/;
    return regExp.test(id);
  };
  const validatePassword = (password) => {
    var regExp = /^[a-zA-z0-9]{8,12}$/;
    return regExp.test(password);
  };
  const validatePasswordConfirm = (password_confirm) => {
    return password === password_confirm;
  };
  const validateRole = (role) => {
    if (role === "ROLE_TEACHER" || role === "ROLE_STUDENT") return true;
    else return false;
  };

  const validateAll = () => {
    if (
      validateEmail(email) &&
      validateId(id) &&
      validatePassword(password) &&
      validatePasswordConfirm(password_confirm) &&
      validateRole(role)
    )
      return true;
    else return false;
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); //prevents reload
    if (!validateAll()) {
      //input contains error
      const err_msg = document.getElementById("overall_err_msg");
      err_msg.innerText = "정보를 다시 확인해주세요!";
      return;
    }
    try {
      const response = await axios.post(
        SIGNUP_URL,
        JSON.stringify({
          username: id,
          password: password,
          name: name,
          email: email,
          role: role,
        }),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      console.log(JSON.stringify(response));
      navigate("/login");
    } catch (err) {
      console.log("UNEXPECTED ERROR");
    }
  };

  return (
    <div id="signup_page_container">
      <div id="inner_wrapper">
        <a herf="/main" className="title">
          채점해VOCA
        </a>
        <div className="description">
          <b>채점해드림</b>에 오신 것을 환영합니다
        </div>
        <div className="description">
          단어학습에 대한 종합적 서비스를 제공해드립니다.
        </div>

        <form onSubmit={handleSubmit}>
          {/* 아이디, 비밀번호, 비밀번호 확인, 이메일, 이름, 닉네임, 선생/학생 */}
          <div className="input_container">
            <label className="input_label">아이디</label>
            <input
              className="text_input"
              type="text"
              value={id}
              onChange={onChangeId}
              placeholder="ID"
            ></input>
          </div>
          <div id="id_error_msg"></div>

          <div className="input_container">
            <label className="input_label">이메일</label>
            <input
              className="text_input"
              type="email"
              value={email}
              onChange={onChangeEmail}
              placeholder="EMAIL"
            ></input>
          </div>
          <div id="email_error_msg"></div>

          <div className="input_container">
            <label className="input_label">비밀번호</label>
            <input
              className="text_input"
              type="password"
              value={password}
              onChange={onChangePassword}
              placeholder="PASSWORD"
            ></input>
          </div>
          <div id="password_error_msg"></div>

          <div className="input_container">
            <label className="input_label">비밀번호 확인</label>
            <input
              className="text_input"
              type="password"
              value={password_confirm}
              onChange={onChangePasswordConfirm}
              placeholder="PASSWORD CONFIRM"
            ></input>
          </div>
          <div id="password_confirm_error_msg"></div>

          <div className="input_container">
            <label className="input_label">이름</label>
            <input
              className="text_input"
              type="text"
              value={name}
              onChange={onChangeName}
              placeholder="NAME"
            ></input>
          </div>

          <div className="role_input_container">
            <div id="radio_container">
              <label className="role_label">
                <input
                  className="role_radio"
                  type="radio"
                  value={"ROLE_TEACHER"}
                  checked={role === "ROLE_TEACHER"}
                  onChange={onChangeRole}
                ></input>
                <div className="role_span_wrapper">
                  <span className="role_span_span">선생님</span>
                </div>
              </label>
              <label className="role_label">
                <input
                  className="role_radio"
                  type="radio"
                  value={"ROLE_STUDENT"}
                  checked={role === "ROLE_STUDENT"}
                  onChange={onChangeRole}
                ></input>
                <div className="role_span_wrapper">
                  <span className="role_span_span">학생</span>
                </div>
              </label>
            </div>
          </div>
          <div id="overall_err_msg"></div>
          <div id="submit_container">
            <button id="submit_button">완료 ➤</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Signup;
