window.addEventListener('load', (event) => {
    document.getElementById('commentForm').addEventListener('submit', function(event) {
        

event.preventDefault(); // Prevent the default form submission behavior

var commentText = document.getElementById('commentText').value;
if (commentText.trim() === '') {
    alert('Comment text cannot be empty.');
    return;
}

var projectName = document.getElementById('projName').textContent; // Get project name
var comment = {
    projectName: projectName,
    username: 'Bobby Bearcat', // Replace with actual username
    content: commentText,
    replies: [] // Initialize empty array for nested replies
};

// Send a POST request to the server to add the comment
fetch('/api/comments', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(comment)
})
.then(response => {
    if (response.ok) {
        return response.json();
    }
    throw new Error('Network response was not ok.');
})
.then(data => {
    // Handle the response data (the newly added comment)
    var commentElement = createCommentElement(data);
    document.getElementById('comments').appendChild(commentElement);
    document.getElementById('commentText').value = ''; // Clear comment textarea
})
.catch(error => {
    console.error('Error:', error);
    alert('Failed to add comment. Please try again later.');
});
});


        
    });
    
    document.getElementById('submitRequestButton').addEventListener('click', submitRequest);
document.getElementById('cancelRequestButton').addEventListener('click', cancelRequest);
document.getElementById('sendRequestButton').addEventListener('click', sendRequest);
// Create comment element function modified to handle both main comments and subcomments
function createCommentElement(comment, isSubcomment = false) {
var commentDiv = document.createElement('div');
commentDiv.className = 'comment';

var usernamePara = document.createElement('p');
usernamePara.textContent = comment.username;
commentDiv.appendChild(usernamePara);

var commentContentPara = document.createElement('p');
commentContentPara.textContent = comment.content;
commentDiv.appendChild(commentContentPara);

// Check if the comment is a main comment or subcomment
if (!isSubcomment) {
    var replyButton = document.createElement('button');
    replyButton.className = 'reply-button';
    replyButton.textContent = 'Reply';
    replyButton.onclick = function() {
        console.log(comment.id);
        showReplyForm(comment.id); // Pass comment ID to identify which comment to reply to
    };
    commentDiv.appendChild(replyButton);
}

// Handle subcomments if available
if (comment.subcomments && comment.subcomments.length > 0) {
    var subcommentsDiv = document.createElement('div');
    subcommentsDiv.className = 'subcomments';

    comment.subcomments.forEach(function(subcomment) {
        var subcommentElement = createCommentElement(subcomment, true);
        subcommentsDiv.appendChild(subcommentElement);
    });

    commentDiv.appendChild(subcommentsDiv);
}

return commentDiv;
}

function submitSubcomment(parentCommentId) {
var subcommentText = document.getElementById('replyText').value;
if (subcommentText.trim() === '') {
    alert('Subcomment text cannot be empty.');
    return;
}

var subcomment = {
    username: 'Bobby Bearcat', 
    content: subcommentText
};

fetch('/api/comments/' + parentCommentId + '/replies', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(subcomment)
})
.then(response => {
    if (response.ok) {
        return response.json();
    }
    throw new Error('Network response was not ok.');
})
.then(data => {
    console.log('Subcomment added successfully:', data);
})
.catch(error => {
    console.error('Error:', error);
    alert('Failed to add subcomment. Please try again later.');
});
}


function submitReply(parentCommentId) {
    var replyText = document.getElementById('replyText' + parentCommentId).value;
    if (replyText.trim() === '') {
        alert('Reply text cannot be empty.');
        return;
    }

    var reply = {
        username: 'Bobby Bearcat', 
        content: replyText
    };

    fetch('/api/comments/' + parentCommentId + '/replies', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reply)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        console.log('Reply added successfully:', data);
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to add reply. Please try again later.');
    });
}

function sendRequest() {
    var sendRequestForm = document.getElementById('sendRequestForm');
    sendRequestForm.classList.remove('hide');
    sendRequestForm.classList.add('show');
}

function submitRequest() {
var contactName = document.getElementById("name").value;
var contactEmail = document.getElementById("email").value;

if (contactName && contactEmail) {
    var xhr = new XMLHttpRequest();
    var projectNameElement = document.getElementById("projName"); 

    var projectName = projectNameElement.textContent;
    var url = "/saveRequests?contactName=" + encodeURIComponent(contactName) + "&contactEmail=" + encodeURIComponent(contactEmail) + "&projName=" + encodeURIComponent(projectName);

    window.location.href = url;
} else {
    alert("Please fill out all fields.");
}
}



function cancelRequest() {
    hideRequestForm();
}

function hideRequestForm() {
    var sendRequestForm = document.getElementById('sendRequestForm');
    sendRequestForm.classList.remove('show');
    sendRequestForm.classList.add('hide');
}



