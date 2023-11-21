import React from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

import "../styles/vocablist_select.css";

const VOCABLIST_SELECT_URL = "/api/vocablist/";

const VocablistSelect = () => {
  const { axiosPrivate } = useAxiosPrivate();
  {
    /*const response = axiosPrivate.get(VOCABLIST_SELECT_URL);
    const vocablistList = response.Vocab_list;
     */
  }
  const default_img_path = "/resource/img/vocablist_img_default.png";
  const vocablistList = [
    default_img_path,
    default_img_path,
    default_img_path,
    default_img_path,
    default_img_path,
  ];
  return (
    <div id="vocablist_select_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar />
        </React.Fragment>
        <div id="contents_wrapper">
          <p id="content_label">내 단어장</p>
          <div id="edit_link_container">
            <a className="edit_vocab_link" title="새로운 단어장을 만듭니다!">
              생성
            </a>
            <a
              className="edit_vocab_link"
              title="제공되는 단어장을 클래스에 추가합니다!"
            >
              추가
            </a>
          </div>
          <div id="grid_wrapper">
            {vocablistList.map((vocablist, i) => (
              <a className="vocablist_link" href="/class/vocablist/view">
                <img className="vocablist_img" src={vocablist}></img>
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
export default VocablistSelect;
