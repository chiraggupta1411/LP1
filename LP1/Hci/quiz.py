from tkinter import *

# Create main window
root = Tk()
root.title("Online Quiz")
root.geometry("500x500")
root.config(bg="#f0f0f0")

# Heading
Label(root, text="Online Quiz", font=("Arial", 18, "bold"), bg="#f0f0f0").pack(pady=10)

# Question 1
Label(root, text="1. What is the capital of India?", bg="#f0f0f0", font=("Arial", 12)).pack(anchor="w", padx=20)
q1 = StringVar(value="0")
Radiobutton(root, text="Mumbai", variable=q1, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="New Delhi", variable=q1, value="1", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="Kolkata", variable=q1, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)

# Question 2
Label(root, text="2. Which language is used for web apps?", bg="#f0f0f0", font=("Arial", 12)).pack(anchor="w", padx=20, pady=(10,0))
q2 = StringVar(value="0")
Radiobutton(root, text="Python", variable=q2, value="1", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="C++", variable=q2, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="Assembly", variable=q2, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)

# Question 3
Label(root, text="3. 5 + 3 = ?", bg="#f0f0f0", font=("Arial", 12)).pack(anchor="w", padx=20, pady=(10,0))
q3 = StringVar(value="0")
Radiobutton(root, text="6", variable=q3, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="8", variable=q3, value="1", bg="#f0f0f0").pack(anchor="w", padx=40)
Radiobutton(root, text="10", variable=q3, value="0", bg="#f0f0f0").pack(anchor="w", padx=40)

# Function to calculate score
def submit_quiz():
    score = int(q1.get()) + int(q2.get()) + int(q3.get())
    result = f"Your Score: {score}/3"
    Label(root, text=result, fg="blue", bg="#f0f0f0", font=("Arial", 12, "bold")).pack(pady=10)

# Submit Button
Button(root, text="Submit", command=submit_quiz, bg="#4CAF50", fg="white", width=12).pack(pady=15)

root.mainloop()
