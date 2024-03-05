window.onload = () => {
    fetch("http://localhost:8080/tasks/1")
        .then((res) => res.json())
        .then((res) => console.log(res));
};
