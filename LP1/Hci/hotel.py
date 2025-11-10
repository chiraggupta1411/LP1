import tkinter as tk
from tkinter import messagebox

# Create main window
root = tk.Tk()
root.title("Customer Feedback Form")
root.geometry("400x320")
root.configure(bg="#e6f0ff")

# Heading
tk.Label(root, text="🍴 Hotel Food Feedback Form", font=("Arial", 15, "bold"), bg="#e6f0ff").pack(pady=10)

# Customer Name
tk.Label(root, text="Name:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
name_entry = tk.Entry(root, width=30)
name_entry.pack(pady=5)

# Food Quality Feedback (Checkbuttons)
tk.Label(root, text="How was the food quality?", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
quality1 = tk.IntVar()
quality2 = tk.IntVar()
quality3 = tk.IntVar()
quality4 = tk.IntVar()

tk.Checkbutton(root, text="Excellent", variable=quality1, bg="#e6f0ff").pack(anchor="w", padx=60)
tk.Checkbutton(root, text="Good", variable=quality2, bg="#e6f0ff").pack(anchor="w", padx=60)
tk.Checkbutton(root, text="Average", variable=quality3, bg="#e6f0ff").pack(anchor="w", padx=60)
tk.Checkbutton(root, text="Poor", variable=quality4, bg="#e6f0ff").pack(anchor="w", padx=60)

# Comments Section (Text)
tk.Label(root, text="Comments:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=(8, 0))
comments = tk.Text(root, width=30, height=4)
comments.pack(pady=5)

# Submit Function
def submit_feedback():
    name = name_entry.get()
    food_feedback = []
    if quality1.get(): food_feedback.append("Excellent")
    if quality2.get(): food_feedback.append("Good")
    if quality3.get(): food_feedback.append("Average")
    if quality4.get(): food_feedback.append("Poor")
    comment_text = comments.get("1.0", tk.END)

    message = (
        f"Name: {name}\n"
        f"Food Quality: {', '.join(food_feedback)}\n\n"
        f"Comments:\n{comment_text}"
    )

    messagebox.showinfo("Feedback Submitted", message)

# Submit Button
tk.Button(root, text="Submit", width=12, bg="#4CAF50", fg="white", command=submit_feedback).pack(pady=15)

# Run App
root.mainloop()
