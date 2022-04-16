import {API_URL} from "../Constants/APIConst";
import axios from "axios";
import AuthenticationService from "./AuthenticationService";

class ImageService {

    postImage(formData) {
        const auth = AuthenticationService.getAuth();
        return axios.post(API_URL + '/images/add', formData, {

                auth: {
                    username: auth.user,
                    password: auth.pass
                }
            }
        )
    }

    getImage() {
        const auth = AuthenticationService.getAuth();
        return axios.get(API_URL + '/images/add', {
            auth: {
                username: auth.user,
                password: auth.pass
            }
        })
    }

    deleteImage(formData) {
        const auth = AuthenticationService.getAuth();
        return axios.post(API_URL + '/images/delete', formData,{
                auth: {
                    username: auth.user,
                    password: auth.pass
                }
            }
        )
    }
}

export default new ImageService()