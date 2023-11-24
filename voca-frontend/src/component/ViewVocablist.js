import React, { useState, useEffect } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { generatePDF } from "../api/pdf.js";

import "../styles/view_vocablist.css";
import { useParams } from "react-router-dom";

const AUTO_TEST_URL = "/api/test/auto";
const VOCABLIST_CONTENT_URL = "/api/vocablist/";

const ViewVocabList = () => {
  const { class_id, vocablist_id } = useParams();
  const [vocabContent, setVocabContent] = useState([]);
  const [test_name, setTestName] = useState("");
  const [test_date, setTestDate] = useState("");
  const [question_count, setQuestionCount] = useState(0);
  const [pass_score, setPassScore] = useState(0);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    try {
      axiosPrivate
        .get(VOCABLIST_CONTENT_URL + `${vocablist_id}`)
        .then((response) => {
          setVocabContent(response.data.vocabListContents);
        });
    } catch (error) {
      console.log("ERROR FETCHING VOCABLIST CONTENT");
    }
  }, []);

  const handleTestNameChange = (e) => {
    setTestName(e.target.value);
  };

  const handleTestDateChange = (e) => {
    setTestDate(e.target.value);
  };

  const handleQuestionCountChange = (e) => {
    const parsedInput = parseInt(e.target.value);
    setQuestionCount(parsedInput);
  };

  const handPassScoreChange = (e) => {
    const prasedInput = parseInt(e.target.value);
    setPassScore(prasedInput);
  };

  const validateInput = () => {
    if (test_name.length < 1) {
      return false;
    }
    try {
      const parsedDate = new Date(test_date);
    } catch (error) {
      return false;
    }

    if (question_count > vocabContent.length) {
      return false;
    }
    if (pass_score > question_count) {
      return false;
    }
    return true;
  };

  const handleAutoTest = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "flex";
  };

  const closeButtonHandler = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const submitHandler = (e) => {
    e.preventDefault();
    const isValid = validateInput();
    if (!isValid) {
      const element = document.getElementById("error_message");
      element.innerText = "입력을 다시 확인해주세요";
      return;
    }
    try {
      axiosPrivate.post(
        AUTO_TEST_URL,
        JSON.stringify({
          name: test_name,
          time: new Date(test_date).toISOString(),
          vocabListId: vocablist_id,
          classId: class_id,
          number: question_count,
          pass_score: pass_score,
          questionType: "KOR_TO_ENG",
        }),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
    } catch (error) {
      console.log("ERROR SUBMITTING AUTO TEST");
    }
  };

  return (
    <div id="vocablist_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="vocablist" />
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
              {vocabContent.map((vocab, i) => (
                <p key={i} className="voca_item">
                  {vocab.word}
                </p>
              ))}
            </div>
            <div className="vocablist_item">
              {vocabContent.map((vocab, i) => (
                <p key={i} className="voca_item">
                  {vocab.meaning}
                </p>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div id="auto_test_modal">
        <div id="modal_window">
          <div id="close_button_container">
            <button id="close_button" onClick={(e) => closeButtonHandler(e)}>
              X
            </button>
          </div>
          <div id="form_container">
            <form id="test_info_form">
              <label> 시험 이름</label>
              <input
                type="text"
                onChange={(e) => handleTestNameChange(e)}
                placeholder="한글자 이상으로 입력해주세요"
              ></input>
              <label> 시험 날짜</label>
              <input
                type="text"
                onChange={(e) => handleTestDateChange(e)}
                placeholder="YYYY-MM-DD 형식으로 입력해주세요"
              ></input>
              <label> 시험 문항 수</label>
              <input
                type="text"
                onChange={(e) => handleQuestionCountChange(e)}
                placeholder="단어장 단어 수보다 작은 수를 입력해주세요"
              ></input>
              <label> 통과 최저 점수</label>
              <input
                type="text"
                onChange={(e) => handPassScoreChange(e)}
                placeholder="문항 수보다 작은 수를 입력해주세요"
              ></input>
              <p id="error_message"></p>
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
