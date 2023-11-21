import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import RequireAuth from "./component/RequireAuth";
import PersistLogin from "./component/PersistLogin";
import MainPage from "./component/MainPage";
import Signup from "./component/Signup";
import Signin from "./component/Signin";
import ClassSelect from "./component/ClassSelect";
import ClassMain from "./component/ClassMain";
import CreateClass from "./component/CreateClass";
import VocablistSelect from "./component/VocablistSelect";
import ViewVocabList from "./component/ViewVocablist";
import ClassMemberEdit from "./component/ClassMemberEdit";
import ClassTests from "./component/ClassTests";
import TestManualCreate from "./component/TestManualCreate";
import ViewTestResults from "./component/ViewTestResults";
import CreateVocablist from "./component/CreateVocablist";
import RegisterVocablist from "./component/RegisterVocablist";
import StudentTestResult from "./component/StudentTestResult";
import { AuthProvider } from "./context/AuthProvider";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/main" element={<MainPage />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/login" element={<Signin />}></Route>

          <Route path="/class/vocablist" element={<VocablistSelect />}></Route>
          <Route
            path="/class/vocablist/view"
            element={<ViewVocabList />}
          ></Route>
          <Route path="/class/member" element={<ClassMemberEdit />}></Route>
          <Route path="/class/tests" element={<ClassTests />}></Route>
          <Route
            path="/class/test/results/:key/"
            element={<ViewTestResults />}
          ></Route>
          <Route path="/vocablist/create" element={<CreateVocablist />}></Route>
          <Route
            path="/class/test/create/manual"
            element={<TestManualCreate />}
          ></Route>
          <Route
            path="/class/vocablist/register"
            element={<RegisterVocablist />}
          ></Route>
          <Route path="/class/test/result/:student_key/"></Route>
          {/* routes that require logins */}
          <Route element={<PersistLogin />}></Route>
          <Route element={<RequireAuth />}>
            <Route path="/create_class" element={<CreateClass />}></Route>
            <Route path="/classes " element={<ClassSelect />}></Route>
            <Route path="/class/main" element={<ClassMain />}></Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
