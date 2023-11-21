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
    setAuth({ id, accessToken });
    return <Outlet />;
  }
  return auth?.id ? ( //if auth contains id (if user is logined)
    <Outlet />
  ) : (
    <Navigate to="/login" />
  );
};

export default RequireAuth;
