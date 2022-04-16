import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";

import RegistrationForm from "./RegistrationForm";
import LoginForm from "./LoginForm";
import SuccessPage from "./SuccessPage";
import RequireAuth from "./RequireAuth";

const ImageApp = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/signup" element={<RegistrationForm/>}/>
                <Route path="/" element={<LoginForm/>}/>
                <Route element={<RequireAuth allowedRoles={"ROLE_USER"}/>}>
                    <Route path="/success" element={<SuccessPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default ImageApp;