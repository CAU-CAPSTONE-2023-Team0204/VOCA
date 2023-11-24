import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import "../styles/class_member.css";

const MEMBER_URL = "/api/classes/user/";
const MEMBER_DELETE_URL = "/api/classes";

const ClassMemberEdit = () => {
  const { class_id } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const [memberList, setMemberList] = useState([]);
  const [deleteMember, setDeleteMember] = useState();

  useEffect(() => {
    try {
      axiosPrivate.get(MEMBER_URL + `${class_id}`).then((response) => {
        setMemberList(response.data.memberList);
      });
    } catch (error) {
      console.log("ERROR FETCHING CLASS USER DATA");
    }
  }, []);
  //   const response = axiosPrivate.get(MEMBER_URL);
  //   const memberList = response.members;

  const copyButtonHandler = () => {
    const element = document.getElementById("class_code");
    const classCode = element.innerText;
    window.navigator.clipboard.writeText(classCode).then(() => {
      alert("복사 완료!");
    });
  };

  const deleteButtonHandler = (index) => {
    const element = document.getElementById("modal_window");
    setDeleteMember(memberList[index].id);
    element.style.display = "flex";
  };

  const closeButtonHandler = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const confirmButtonHandler = () => {
    try {
      axiosPrivate
        .delete(MEMBER_DELETE_URL + `/${class_id}/${deleteMember}`)
        .then((response) => {
          console.log(response);
        });
    } catch (error) {
      console.log("SERVER ERROR", error);
    }
  };

  return (
    <div>
      <div id="member_edit_page_container">
        <React.Fragment>
          <NavigationBar />
        </React.Fragment>

        <div id="main_wrapper">
          <React.Fragment>
            <TeacherSidebar class_id={class_id} selected="class" />
          </React.Fragment>
          <div id="contents_wrapper">
            <div id="page_title">클래스 멤버</div>
            <div id="divider" />
            <div id="class_code_container">
              추가코드
              <div id="class_code"> a0vduwm9dkmvc </div>
              <button id="copy_button" onClick={() => copyButtonHandler()}>
                복사
              </button>
            </div>
            <div id="member_container">
              {memberList.map((member, i) => (
                <div key={i} className="member">
                  <p className="name">{member.name}</p>
                  <p className="id">{member.email}</p>
                  <button
                    className="delete_button"
                    onClick={() => deleteButtonHandler(i)}
                  >
                    X
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div id="confirm_modal">
        <div id="modal_window">
          <div id="close_button_container">
            <button id="close_button" onClick={() => closeButtonHandler()}>
              X
            </button>
          </div>
          <div id="message_area">
            정말로 이 멤버를 <b>삭제</b> 하겠습니까?
          </div>
          <div id="confirm_button_container">
            <button id="confirm_button" onClick={() => confirmButtonHandler()}>
              확인
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClassMemberEdit;
