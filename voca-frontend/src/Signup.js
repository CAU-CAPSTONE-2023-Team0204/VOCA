import { useState } from "react";
import "./styles/signup.css";

const Signup = () => {
  var [id, setId] = useState("");
  var [email, setEmail] = useState("");
  var [password, setPassword] = useState("");
  var [password_confirm, setPasswordConfirm] = useState("");
  var [name, setName] = useState("");

  const onChangeEmail = (event) => {
    email = event.currentTarget.value;
    setEmail(email.toLowerCase());
    // var valEmail = validateEmail(email);
  };

  const validateEmail = (email) => {
    return true;
    // return email
    //   .toLowerCase()
    //   .match(/([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/);
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
  return (
    <div id="wrapper">
      <div id="inner_wrapper">
        <a herf="/main" className="title">
          채점해드림
        </a>
        <div className="description">
          <b>채점해드림</b>에 오신 것을 환영합니다
        </div>
        <div className="description">
          단어학습에 대한 종합적 서비스를 제공해드립니다.
        </div>

        <form>
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
            <div className="input_error_message"></div>
          </div>
          <div className="input_container">
            <label className="input_label">이메일</label>
            <input
              className="text_input"
              type="email"
              value={email}
              onChange={onChangeEmail}
              placeholder="EMAIL"
            ></input>
            <div className="input_error_message"></div>
          </div>

          <div className="input_container">
            <label className="input_label">비밀번호</label>
            <input
              className="text_input"
              type="password"
              value={password}
              onChange={onChangePassword}
              placeholder="PASSWORD"
            ></input>
            <div className="input_error_message"></div>
          </div>

          <div className="input_container">
            <label className="input_label">비밀번호 확인</label>
            <input
              className="text_input"
              type="password_confirm"
              value={password_confirm}
              onChange={onChangePasswordConfirm}
              placeholder="PASSWORD CONFIRM"
            ></input>
            <div className="input_error_message"></div>
          </div>

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
          <div id="submit_container">
            <button id="submit_button" type="submit">
              완료 ➤
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Signup;
