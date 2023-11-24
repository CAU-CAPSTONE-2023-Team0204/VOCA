import "../styles/teacher_sidebar.css";
import { useParams } from "react-router-dom";
import useAuth from "../hooks/useAuth.js";
import { useEffect, useState } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate.js";

const USER_ME_URL = "/api/member/me";
const CLASS_INFO_URL = "/api/classes/";

const TeacherSidebar = (props) => {
  const class_id = props.class_id;
  const [userName, setUserName] = useState("");
  const [className, setClassName] = useState("");
  const { auth } = useAuth();
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    try {
      axiosPrivate.get(USER_ME_URL).then((response) => {
        setUserName(response.data.username);
      });
    } catch (error) {
      console.log("ERROR FETCHING USER DATA");
    }

    try {
      axiosPrivate.get(CLASS_INFO_URL + `${class_id}`).then((response) => {
        setClassName(response.data.name);
      });
    } catch (error) {
      console.log("ERROR FETCHING CLASS DATA");
    }
  });

  const handleLogoutButton = (e) => {
    console.log(auth);
    auth.id = "";
    auth.accessToken = "";
    localStorage.clear();
    window.location.reload();
  };

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
            <a href={props.userid}>{userName ? userName : "NO_USER_NAME"}</a>
            <p id="classname">{className ? className : "NO_CLASS_NAME"}</p>
            <button id="logout_button" onClick={(e) => handleLogoutButton(e)}>
              Logout ↪
            </button>
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
          <li
            className={
              props.selected === "class" ? "list_item_selected" : "list_item"
            }
          >
            <a
              className={
                props.selected === "class" ? "link_item_selected" : "link_item"
              }
              href={`/class/${class_id}/member`}
            >
              Class
            </a>
          </li>
          <li
            className={
              props.selected === "vocablist"
                ? "list_item_selected"
                : "list_item"
            }
          >
            <a
              className={
                props.selected === "vocablist"
                  ? "link_item_selected"
                  : "link_item"
              }
              href={`/class/${class_id}/vocablist`}
            >
              단어장
            </a>
          </li>
          <li
            className={
              props.selected === "test" ? "list_item_selected" : "list_item"
            }
          >
            <a
              className={
                props.selected === "test" ? "link_item_selected" : "link_item"
              }
              href={`/class/${class_id}/tests`}
            >
              시험
            </a>
          </li>
        </ul>
      </div>
    </div>
  );
};
export default TeacherSidebar;
