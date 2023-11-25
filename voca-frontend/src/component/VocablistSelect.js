import React from "react";
import { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import "../styles/vocablist_select.css";
import { useParams } from "react-router-dom";

const VOCABLIST_SELECT_URL = "/api/vocablist/class/";

const VocablistSelect = () => {
  const { class_id } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const [vocablistList, setvocablistList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setIsLoading(true);
    try {
      axiosPrivate
        .get(VOCABLIST_SELECT_URL + `${class_id}`)
        .then((response) => {
          setvocablistList(response.data.vocabLists);
          setIsLoading(false);
        });
    } catch (error) {
      console.log("UNEXPECTED ERROR", error);
    }
  }, []);

  return (
    <div id="vocablist_select_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="vocablist" />
        </React.Fragment>
        <div id="contents_wrapper">
          <p id="content_label">내 단어장</p>
          <div id="edit_link_container">
            <a
              className="edit_vocab_link"
              title="새로운 단어장을 만듭니다!"
              href={`/class/${class_id}/vocablist/create`}
            >
              생성
            </a>
            <a
              className="edit_vocab_link"
              title="제공되는 단어장을 클래스에 추가합니다!"
              href={`/class/${class_id}/vocablist/register`}
            >
              추가
            </a>
          </div>
          <div id="grid_wrapper">
            {isLoading ? (
              <p>Loading...</p>
            ) : (
              <div>
                {vocablistList?.map((vocablist, i) => (
                  <a
                    key={i}
                    className="vocablist_link"
                    href={`/class/${class_id}/vocablist/${vocablist.id}/view`}
                  >
                    <img className="vocablist_img" src={vocablist}></img>
                    <p className="vocablist_name">{vocablist.name}</p>
                  </a>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
export default VocablistSelect;
