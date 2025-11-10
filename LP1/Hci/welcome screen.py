import tkinter as tk

# Create the main window
root = tk.Tk()
root.title("Welcome Screen")
root.geometry("400x250")  # Set window size

# Create a label for the welcome text
label = tk.Label(root, text="Welcome to My Application", font=("Arial", 16))
label.pack(pady=50)  # Add some vertical space

# Create an Exit button
exit_button = tk.Button(root, text="Exit", font=("Arial", 12), command=root.destroy)
exit_button.pack(pady=20)

# Run the application
root.mainloop()
