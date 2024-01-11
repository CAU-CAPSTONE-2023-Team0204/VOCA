import { createContext, useState } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({});
  if (!auth) {
    try {
      const id = localStorage.getItem("id");
      const accessToken = localStorage.getItem("accessToken");
      setAuth({ id, accessToken });
    } catch (err) {
      console.log("UNEXPECTED ERROR");
    }
  }
  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
