import axios from 'axios';
import {API_URL} from "../Constants/APIConst";

class AuthenticationService {
    register(username, password) {
        return axios.post(`${API_URL}/registration`, {
            username,
            password
        })
    }

    login(username, password) {
        return axios.post(`${API_URL}/login`, {
            username,
            password
        })
    }

    logout() {
        const auth = this.getAuth();
        return axios.post('http://localhost:8080/api/v1/logout', {
            auth: {
                username: auth.user,
                password: auth.pass
            }
        });
    }

    getAuth() {
        return JSON.parse(localStorage.getItem("user"));
    }
}

export default new AuthenticationService()