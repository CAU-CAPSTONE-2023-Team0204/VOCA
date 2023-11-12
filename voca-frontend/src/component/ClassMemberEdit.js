import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useState } from "react";

import "../styles/class_member.css";

const MEMBER_URL = "";
const MEMBER_DELETE_URL = "";

const ClassMemberEdit = () => {
  const axiosPrivate = useAxiosPrivate();
  const [deleteMember, setDeleteMember] = useState();

  //   const response = axiosPrivate.get(MEMBER_URL);
  //   const memberList = response.members;
  const memberList = [
    { name: "문서", id: "id_1" },
    { name: "장세환", id: "id_22" },
    { name: "차선우우", id: "id_333" },
    { name: "문서", id: "id_1" },
    { name: "장세환", id: "id_22" },
    { name: "차선우우", id: "id_333" },
    { name: "문서", id: "id_1" },
    { name: "장세환", id: "id_22" },
    { name: "차선우우", id: "id_333" },
  ];

  const copyButtonHandler = () => {
    const element = document.getElementById("class_code");
    const classCode = element.innerText;
    window.navigator.clipboard.writeText(classCode).then(() => {
      alert("복사 완료!");
    });
  };

  const deleteButtonHandler = (index) => {
    const element = document.getElementById("modal_window");
    element.style.display = "flex";
    setDeleteMember(index);
  };

  const closeButtonHandler = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const confirmButtonHandler = () => {
    axiosPrivate.delete(MEMBER_DELETE_URL);
  };

  return (
    <div>
      <div id="member_edit_page_container">
        <React.Fragment>
          <NavigationBar />
        </React.Fragment>

        <div id="main_wrapper">
          <React.Fragment>
            <TeacherSidebar />
          </React.Fragment>
          <div id="contents_wrapper">
            <div id="page_title">클래스 멤버</div>
            <div id="divider" />
            <div id="class_code_container">
              추가코드
              <div id="class_code"> a0vduwm9dkmvc </div>
              <button id="copy_button" onClick={() => copyButtonHandler()}>
                {" "}
                복사{" "}
              </button>
            </div>
            <div id="member_container">
              {memberList.map((member, i) => (
                <div key={i} className="member">
                  <p className="name">{member.name}</p>
                  <p className="id">{member.id}</p>
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
