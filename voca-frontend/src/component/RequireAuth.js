import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { useEffect } from "react";

const RequireAuth = () => {
  const { auth, setAuth } = useAuth();
  const location = useLocation();
  if (auth?.id) {
    return <Outlet />;
  } else {
    const id = localStorage.getItem("id");
    const accessToken = localStorage.getItem("accessToken");
    if (id) {
      setAuth({ id, accessToken });
      return <Outlet />;
    } else {
      return <Navigate to="/login" />;
    }
  }
};

export default RequireAuth;
