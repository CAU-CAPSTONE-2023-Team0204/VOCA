import "../styles/teacher_sidebar.css";
import { useParams } from "react-router-dom";

const TeacherSidebar = (props) => {
  const { class_id } = useParams();

  return (
    <div id="sidebar_container">
      <div id="user_info_container">
        <div id="user_info_wrapper">
          <img
            id="user_image"
            src="../../resource/img/default_user_image.webp"
            alt="USER_IMG"
          ></img>
          <div id="user_info">
            <a href={props.userid}>
              {props.name ? props.name : "NO_USER_NAME"}
            </a>
            <p id="classname">
              {props.classname ? props.classname : "NO_CLASS_NAME"}
            </p>
            <button id="logout_button"> Logout ↪ </button>
          </div>
        </div>
      </div>
      <div id="link_container">
        <ul>
          <li
            className={
              props.selected === "main" ? "list_item_selected" : "list_item"
            }
          >
            <a
              className={
                props.selected === "main" ? "link_item_selected" : "link_item"
              }
              href={`/class/${class_id}/main`}
            >
              Home
            </a>
          </li>
          <li className="list_item">
            <a className="link_item" href={`/class/${class_id}/member`}>
              Class
            </a>
          </li>
          <li className="list_item">
            <a className="link_item" href={`/class/${class_id}/vocablist`}>
              단어장
            </a>
          </li>
          <li className="list_item">
            <a className="link_item" href={`/class/${class_id}/tests`}>
              시험
            </a>
          </li>
        </ul>
      </div>
    </div>
  );
};
export default TeacherSidebar;
