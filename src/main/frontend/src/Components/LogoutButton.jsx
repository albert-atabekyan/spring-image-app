import "../css/logout.css";
import {useNavigate} from "react-router-dom";
import AuthenticationService from "../Service/AuthenticationService";

const LogoutButton = () => {
    const navigate = useNavigate();

    function onClickHandler() {
        AuthenticationService.logout().finally();
        localStorage.clear();
        navigate("/", {replace: true});
    }

    return (
        <button className="logout-button" onClick={onClickHandler}>Выйти из системы</button>
    );
};

export default LogoutButton;