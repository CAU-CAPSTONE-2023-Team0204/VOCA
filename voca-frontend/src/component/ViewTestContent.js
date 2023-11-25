import React, { useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import { useParams } from "react-router-dom";

import "../styles/test_content.css";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const TEST_CONTENT_URL = "/api/test/start/";

const ViewTestContent = () => {
  const { class_id, test_id } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const [testInfo, setTestInfo] = useState();
  const [isLoading, setIsLoading] = useState(true);

  useState(() => {
    setIsLoading(true);
    try {
      axiosPrivate.get(TEST_CONTENT_URL + test_id).then((response) => {
        setTestInfo(response.data);
        console.log(response.data);
        setIsLoading(false);
      });
    } catch (error) {
      setIsLoading(false);
      console.log("ERROR FETCHING TEST CONTENT", error);
    }
  }, []);

  const handleTestSheet = (e) => {
    //
  };

  const handleGoVocablist = (e) => {
    //
  };

  return (
    <div id="test_content_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="title"> 시험내용 확인 </div>
          <div id="test_info_container">
            <div id="info_wrapper">
              {isLoading ? (
                <p>Loading...</p>
              ) : (
                <div id="test_info_wrapper_left">
                  <p id="test_info_name">{testInfo?.name}</p>
                </div>
              )}
              {isLoading ? (
                <p>Loading...</p>
              ) : (
                <div id="test_info_wrapper_middle">
                  <p className="info">
                    문항 수 : {testInfo?.testContentList.length}
                  </p>
                  <p className="info">통과 점수 : {testInfo?.pass_score}</p>
                  <p className="info">단어장 : {testInfo?.vocabListName}</p>
                </div>
              )}
            </div>
            <div id="info_wrapper_2">
              <div id="test_info_wrapper_right">
                <button
                  className="test_info_button"
                  onClick={(e) => handleTestSheet(e)}
                >
                  시험지 다운로드
                </button>
                <button
                  className="test_info_button"
                  onClick={(e) => handleGoVocablist(e)}
                >
                  단어장 확인
                </button>
              </div>
            </div>
          </div>
          <div>
            <div id="test_label">
              <p className="content_num">번호</p>
              <p className="content">문제</p>
              <p className="content">정답 </p>
            </div>
            {isLoading ? (
              <p>Loading...</p>
            ) : (
              <div>
                {testInfo.testContentList.map((content, i) => (
                  <div className="test_content">
                    <p className="content_num">{i + 1}</p>
                    <p className="content">{content.question}</p>
                    <p className="content">{content.answer}</p>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewTestContent;
