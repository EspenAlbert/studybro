function updateText(p1) {
    document.getElementById("jsResponse").innerHTML = document.getElementById("newCategoryForm:newCategoryType_input").value;
    var selected = document.getElementById("newCategoryForm:newCategoryType_input").value;
    if(selected == "Life Area") {
        document.getElementById("jsResponse").innerHTML = "You selected life area, specify name and description(optional)"
    } else if(selected == "Task Type") {
        document.getElementById("jsResponse").innerHTML = "You selected task type, description is not valid"
    } else {
        document.getElementById("jsResponse").innerHTML = "You selected life area category, the category will be added to your selected life area\n Description is not valid"
    }
//    document.getElementById("newCategoryType").value
}