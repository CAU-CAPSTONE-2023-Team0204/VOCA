import React, { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/class_tests.css";
import { useParams } from "react-router-dom";

const TEST_LIST_URL = "/api/test/";

const ClassTests = () => {
  const { class_id } = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const [testList, setTestList] = useState([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    setIsLoading(true);
    axiosPrivate.get(TEST_LIST_URL + `${class_id}`).then((response) => {
      setTestList(response.data.tests);
      console.log(response.data);
      setIsLoading(false);
    });
  }, []);

  const manualMakeButtonHandler = () => {};

  return (
    <div id="classtests_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="test" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="contents_title_container">
            <div>내 시험</div>
            <div id="button_container">
              <a
                href={`/class/${class_id}/vocablist`}
                className="make_test_button"
              >
                자동출제
              </a>
              <a
                href={`/class/${class_id}/test/create/manual`}
                className="make_test_button"
              >
                수동출제
              </a>
            </div>
          </div>
          <div id="test_list_container">
            <div id="table_label">
              <p className="test_number">번호</p>
              <p className="test_data">이름</p>
              <p className="test_data">응시자</p>
              <p className="test_data">시험 일자</p>
            </div>
            {isLoading ? (
              <p>isLoading...</p>
            ) : testList.length > 0 ? (
              <div>
                {testList.map((test, i) => (
                  <a
                    key={i}
                    href={`/class/${class_id}/test/results/${test.testId}`}
                    className="test_link"
                  >
                    <p className="test_number">{i + 1}</p>
                    <p className="test_data">{test.name}</p>
                    <p className="test_data">
                      {test.submitted_student}/{test.total_student}
                    </p>
                    <p className="test_data">{test.date?.split("T")[0]}</p>
                  </a>
                ))}
              </div>
            ) : (
              <div id="no_test_message">
                테스트가 없습니다. 새 테스트를 생성해보세요.
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ClassTests;
