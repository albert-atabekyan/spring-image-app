import "../css/registrationForm.css";
import "../css/login.css";
import AuthenticationService from "../Service/AuthenticationService";
import Form from "./Form";
import {useState} from "react";
import {Link, Navigate, useNavigate} from "react-router-dom";

const LoginForm = () => {
    const navigate = useNavigate();
    const userInfo = AuthenticationService.getAuth();

    let isAuthenticated = false;
    if(userInfo !== null) isAuthenticated = true;

    const [values, setValues] = useState({
        username: "",
        password: "",
    });

    const inputs = [
        {
            id: 1,
            name: "username",
            type: "text",
            placeholder: "Имя пользователя",
            errorMessage:
                "Пожалуйста, введите корректное имя пользователя.",
            label: "Имя пользователя",
            pattern: "^[A-Za-z0-9]{3,16}$",
            required: true,
        },
        {
            id: 2,
            name: "password",
            type: "password",
            placeholder: "Пароль",
            errorMessage:"Пожалуйста, введите корректный пароль.",
            label: "Пароль",
            pattern: `^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*-]{8,20}$`,
            required: true,
        },
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();

        const user = e.target.username.value;
        const pass = e.target.password.value;
        const response = await AuthenticationService.login(user, pass);

        if(response.data === 101) alert("Пользователь не найден.")
        else if(response.data === 102) alert("Неверный пароль или имя пользователя.")
        else {
            const roles = response.data;
            localStorage.setItem("user", JSON.stringify({user, pass, roles}));
            navigate("/success", {replace: true});
        }
    };

    const onChange = (e) => {
        setValues({ ...values, [e.target.name]: e.target.value });
    };

    return ( isAuthenticated ? <Navigate to="/success"/>:
            <>
                <Form onSubmit={handleSubmit}
                      onChange={onChange}
                      inputs={inputs}
                      values={values}
                      title="Авторизация"
                      buttonTitle="Войти">
                </Form>
                <div className="registration"><div>Нет аккаунта? </div><Link to="/signup">Зарегистрируйтесь</Link></div>
            </>
    );
};

export default LoginForm;