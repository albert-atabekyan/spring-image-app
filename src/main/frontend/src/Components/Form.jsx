import FormInput from "./FormInput";

const Form = (props) => {
    const {onSubmit, onChange, inputs, values, title, buttonTitle} = props;

    return (
        <form onSubmit={onSubmit}>
        <h1>{title}</h1>
        {inputs.map((input) => (
            <FormInput
                key={input.id}
                {...input}
                value={values[input.name]}
                onChange={onChange}
            />
        ))}
        <button>{buttonTitle}</button>
    </form>);
};

export default Form;