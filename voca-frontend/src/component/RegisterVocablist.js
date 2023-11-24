import React, { useEffect, useState } from "react";
import NavigationBar from "./NavigationBar";
import TeacherSidebar from "./TeacherSidebar";
import axios, { axiosPrivate } from "../api/axios";
import { useParams } from "react-router-dom";
import "../styles/register_vocablist.css";

const GET_VOCABLIST_URL = "/api/vocablist";
const GET_WORDS_URL = "/api/vocablist/";
const REGISTER_VOCABLIST_URL = "/api/vocablist/";

const RegisterVocablist = () => {
  const { class_id } = useParams();
  const [isLoading, setIsLoading] = useState(true);
  const [vocabLists, setVocabLists] = useState([]);
  const [selectedVocabList, setSelectedVocabList] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [wordData, setWordData] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    const response = axios
      .get(GET_VOCABLIST_URL)
      .then((response) => {
        setVocabLists(response.data.vocabLists);
        setIsLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching data", err);
        setIsLoading(false);
      });
  }, []);

  const handleSelectVocablist = async (i) => {
    setSelectedVocabList(i);
    const response = await axiosPrivate.get(
      GET_WORDS_URL + `${vocabLists[selectedVocabList].id}`
    );

    setWordData(response.data.vocabListContents);
    const element = document.getElementById("modal_window");
    element.style.display = "block";
  };

  const handleCloseModal = () => {
    const element = document.getElementById("modal_window");
    element.style.display = "none";
  };

  const handleRegisterButton = () => {
    axios.post(
      `/api/vocablist/${vocabLists[selectedVocabList].id}/${class_id}`
    );
  };

  return (
    <div id="register_vocablist_page_container">
      <React.Fragment>
        <NavigationBar />
      </React.Fragment>

      <div id="main_wrapper">
        <React.Fragment>
          <TeacherSidebar class_id={class_id} selected="vocablist" />
        </React.Fragment>
        <div id="contents_wrapper">
          <div id="title">단어장 등록</div>
          <p id="page_description">단어장을 클래스에 등록해보세요!</p>
          <div>
            {isLoading ? (
              <p> Loading ... </p>
            ) : (
              <div id="vocablists_container">
                {vocabLists.map((vocablist, i) => (
                  <div
                    key={i}
                    id="vocablist_container"
                    onClick={(e) => handleSelectVocablist(i)}
                  >
                    <img src={vocablist.image} className="vocablist_img"></img>
                    <p className="vocablist_label">{vocablist.name}</p>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
      <div id="register_modal">
        <div id="modal_window">
          <div id="close_button_container">
            <button id="close_button" onClick={() => handleCloseModal()}>
              X
            </button>
          </div>
          <div id="title_img_container">
            <img
              src={vocabLists[selectedVocabList]?.image}
              id="selected_vocab_img"
            />
            <div id="info_container">
              <p id="selected_title">{vocabLists[selectedVocabList]?.name}</p>
              <p id="selected_description">
                {vocabLists[selectedVocabList]?.description}
              </p>
            </div>
          </div>
          <div id="word_preview_container">
            {wordData?.map((word, i) => (
              <div key={i} id="word_container">
                <p className="word_p">{word.word}</p>
                <p className="word_p">{word.meaning}</p>
              </div>
            ))}
          </div>
          <div id="register_button_container">
            <button
              id="register_button"
              onClick={(e) => handleRegisterButton()}
            >
              클래스에 등록
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterVocablist;
