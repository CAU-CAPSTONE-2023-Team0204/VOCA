import "../styles/signin.css";

const Signin = () => {
  return (
    <div id="signin_page_container">
      <div id="signin_wrapper">
        <div id="logo_wrapper">
          <a href="/main" id="logo">
            채점해드림
          </a>
        </div>
        <form id="signin_form">
          <div className="input_container">
            <input
              className="signin_input"
              type="text"
              placeholder="ID"
            ></input>
          </div>

          <div className="input_container">
            <input
              className="signin_input"
              type="password"
              placeholder="PASSWORD"
            ></input>
          </div>
        </form>

        <div id="button_container">
          <button className="submit_button">로그인</button>
        </div>
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
