import "../styles/navigationbar.css";

const NavigationBar = () => {
  return (
    <div id="navbar_content_wrapper">
      <div id="navbar_content_left">
        <div id="main_logo_text">채점해VOCA</div>
      </div>
      <div id="navbar_content_right">
        <a href="/classes" className="navbar_a_text">
          클래스목록
        </a>
        <div className="vertical_line"></div>
      </div>
    </div>
  );
};

export default NavigationBar;
