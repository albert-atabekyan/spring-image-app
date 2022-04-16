import "../css/registrationForm.css";
import AuthenticationService from "../Service/AuthenticationService";
import Form from "./Form";
import { useState } from "react";
import {useNavigate} from "react-router-dom";

const RegistrationForm = () => {
    const navigate = useNavigate();

    const [values, setValues] = useState({
        username: "",
        password: "",
        confirmPassword: "",
    });

    const inputs = [
        {
            id: 1,
            name: "username",
            type: "text",
            placeholder: "Имя пользователя",
            errorMessage:
                "В имени пользователя должно быть от 3 до 16 символов, " +
                "и имя не должно содержать специальные символы!",
            label: "Имя пользователя",
            pattern: "^[A-Za-z0-9]{3,16}$",
            required: true,
        },
        {
            id: 2,
            name: "password",
            type: "password",
            placeholder: "Пароль",
            errorMessage:
                "Длина пароля должна быть от 8 до 20 символов, и " +
                "пароль должен содержать как минимум 1 букву, 1 цифру и " +
                "1 специальный символ !",
            label: "Пароль",
            pattern: `^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*-]{8,20}$`,
            required: true,
        },
        {
            id: 3,
            name: "confirmPassword",
            type: "password",
            placeholder: "Подтвеждение пароля",
            errorMessage: "Пароли не совпадают!",
            label: "Подтверждение пароля",
            pattern: values.password,
            required: true,
        },
    ];

    const handleSubmit = (e) => {
        e.preventDefault();

        AuthenticationService.register(e.target.username.value, e.target.password.value).finally();

        navigate("/success", {replace:true})
    };

    const onChange = (e) => {
        setValues({ ...values, [e.target.name]: e.target.value });
    };

    return (
        <Form onSubmit={handleSubmit}
              onChange={onChange}
              inputs={inputs}
              values={values}
              title="Регистрация"
              buttonTitle="Зарегистрироваться"
        >
        </Form>
    );
};

export default RegistrationForm;