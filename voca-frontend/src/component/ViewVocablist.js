import React, { useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { generatePDF } from "../api/pdf.js";

import "../styles/view_vocablist.css";

const AUTO_TEST_URL = "mola";

const ViewVocabList = () => {
  const axiosPrivate = useAxiosPrivate();

  const [test_name, setTestName] = useState("");
  const [] = useState("");

  const handleAutoTest = () => {
    const element = document.getElementById("modal_window");
    console.log("hell");
    generatePDF("11월 3주차 시험", [
      "video",
      "bass",
      "cat",
      "intelligence",
      "content",
      "food",
      "university",
    ]);
    element.style.display = "flex";
  };

  const closeButtonHandler = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const submitHandler = () => {
    axiosPrivate.post(
      AUTO_TEST_URL,
      JSON.stringify({
        name: test_name,
      }),
      {
        headers: { "Content-Type": "application/json" },
      }
    );
  };

  return (
    <div id="vocablist_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="vocablist_info_container">
            <div>
              <p id="vocablist_name">단어장 이름 | 단어장 차시</p>
              <p>단어장 설명</p>
            </div>
            <div id="button_container">
              <button> back </button>
              <button onClick={handleAutoTest}> 자동 출제 </button>
            </div>
          </div>
          <div id="vocablist_container">
            <div className="vocablist_item">
              <p className="voca_item">단어1</p>
              <p className="voca_item">단어1</p>
              <p className="voca_item">단어1</p>
            </div>
            <div className="vocablist_item">
              <p className="voca_item">뜻1</p>
              <p className="voca_item">뜻1</p>
              <p className="voca_item">뜻1</p>
            </div>
          </div>
        </div>
      </div>
      <div id="auto_test_modal">
        <div id="modal_window">
          <div id="close_button_container">
            <button id="close_button" onClick={() => closeButtonHandler()}>
              X
            </button>
          </div>
          <div id="form_container">
            <form id="test_info_form">
              <label> 시험 이름</label>
              <input type="text"></input>
              <label> 시험 날짜</label>
              <input type="text"></input>
              <label> 시험 문항 수</label>
              <input type="text"></input>
              <label> 통과 최저 점수</label>
              <input type="text"></input>
              <div id="submit_button_container">
                <button onClick={submitHandler}> 문제 생성 ! </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewVocabList;
