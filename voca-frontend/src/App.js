import logo from "./logo.svg";
import "./App.css";
import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
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
import ViewTestContent from "./component/ViewTestContent";
import { AuthProvider } from "./context/AuthProvider";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/main" element={<MainPage />}></Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/login" element={<Signin />}></Route>

          {/* routes that require logins */}
          <Route element={<PersistLogin />}></Route>
          <Route element={<RequireAuth />}>
            <Route path="/create_class" element={<CreateClass />}></Route>
            <Route path="/classes" element={<ClassSelect />}></Route>
            <Route path="/class/:class_id/main" element={<ClassMain />}></Route>
            <Route
              path="/class/:class_id/vocablist"
              element={<VocablistSelect />}
            ></Route>
            <Route
              path="/class/:class_id/vocablist/:vocablist_id/view"
              element={<ViewVocabList />}
            ></Route>
            <Route
              path="/class/:class_id/member"
              element={<ClassMemberEdit />}
            ></Route>
            <Route
              path="/class/:class_id/tests"
              element={<ClassTests />}
            ></Route>
            <Route
              path="/class/:class_id/test/results/:test_id/"
              element={<ViewTestResults />}
            ></Route>
            <Route
              path="/class/:class_id/vocablist/create"
              element={<CreateVocablist />}
            ></Route>
            <Route
              path="/class/:class_id/test/create/manual"
              element={<TestManualCreate />}
            ></Route>
            <Route
              path="/class/:class_id/vocablist/register"
              element={<RegisterVocablist />}
            ></Route>
            <Route
              path="/class/:class_id/test/result/:student_key"
              element={<StudentTestResult />}
            ></Route>
            <Route
              path="/class/:class_id/test/content/:test_id"
              element={<ViewTestContent />}
            ></Route>
          </Route>
          <Route path="*" element={<NotFound />}></Route>
          {/*<Route path="*" element={<MainPage />} />
          REDIRECT if no appropriate page is found*/}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

function NotFound() {
  return <Navigate to="/main" />;
}
