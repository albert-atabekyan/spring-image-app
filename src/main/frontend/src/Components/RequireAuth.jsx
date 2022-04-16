import { useLocation, Navigate, Outlet } from "react-router-dom";
import AuthenticationService from "../Service/AuthenticationService";

const RequireAuth = ({ allowedRoles }) => {
    const auth = AuthenticationService.getAuth();
    const location = useLocation();

    return (
        auth?.roles?.find(role => allowedRoles?.includes(role?.authority))
            ? <Outlet />
            : auth?.user
                ? <Navigate to="/unauthorized" state={{ from: location }} replace />
                : <Navigate to="/" state={{ from: location }} replace />
    );
}

export default RequireAuth;