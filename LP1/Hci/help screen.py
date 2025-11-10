import tkinter as tk

# Create the main window
root = tk.Tk()
root.title("Help Screen")
root.geometry("450x300")  # Set window size
root.configure(bg="black")  # Set background color

# Heading Label
heading = tk.Label(
    root,
    text="Help & Support",
    font=("Arial", 16, "bold"),
    bg="black",
    fg="white"
)
heading.pack(pady=15)

# Instruction Text
help_text = """If you need any help using this application,
please follow these steps:

1. Go to 'Settings' to check your configuration.
2. Visit the 'About' section for app details.
3. For further assistance, contact support:

   📧 Email: support@example.com
   ☎️ Phone: +91 9876543210
"""

# Display the help text
help_label = tk.Label(
    root,
    text=help_text,
    justify="left",
    font=("Arial", 11),
    bg="black",
    fg="white"
)
help_label.pack(padx=20, pady=10)

# Exit Button
exit_button = tk.Button(
    root,
    text="Close",
    font=("Arial", 12),
    bg="gray20",
    fg="white",
    activebackground="gray40",
    activeforeground="white",
    command=root.destroy
)
exit_button.pack(pady=15)

# Run the app
root.mainloop()
