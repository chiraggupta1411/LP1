import tkinter as tk

# Create the main window
root = tk.Tk()
root.title("Fund Transfer")
root.geometry("400x350")
root.configure(bg="#e6f0ff")  # Light blue background

# Heading Label
heading = tk.Label(
    root,
    text="💸 Fund Transfer",
    font=("Arial", 18, "bold"),
    bg="#e6f0ff",
    fg="#003366"
)
heading.pack(pady=20)

# Labels and Entry Fields
sender_label = tk.Label(root, text="From Account:", font=("Arial", 12), bg="#e6f0ff")
sender_label.pack(pady=5)
sender_entry = tk.Entry(root, width=30)
sender_entry.pack(pady=5)

receiver_label = tk.Label(root, text="To Account:", font=("Arial", 12), bg="#e6f0ff")
receiver_label.pack(pady=5)
receiver_entry = tk.Entry(root, width=30)
receiver_entry.pack(pady=5)

amount_label = tk.Label(root, text="Amount (₹):", font=("Arial", 12), bg="#e6f0ff")
amount_label.pack(pady=5)
amount_entry = tk.Entry(root, width=30)
amount_entry.pack(pady=5)

# Message Label
message_label = tk.Label(root, text="", font=("Arial", 12), bg="#e6f0ff", fg="green")
message_label.pack(pady=10)

# Function to handle transfer button click
def transfer_funds():
    sender = sender_entry.get()
    receiver = receiver_entry.get()
    amount = amount_entry.get()

    if sender and receiver and amount:
        message_label.config(text=f"✅ ₹{amount} transferred successfully!")
    else:
        message_label.config(text="⚠️ Please fill all fields", fg="red")

# Transfer Button
transfer_button = tk.Button(
    root,
    text="Transfer",
    font=("Arial", 12, "bold"),
    bg="#0073e6",
    fg="white",
    padx=10,
    pady=5,
    command=transfer_funds
)
transfer_button.pack(pady=15)

# Exit Button
exit_button = tk.Button(
    root,
    text="Exit",
    font=("Arial", 12),
    bg="#999999",
    fg="white",
    padx=10,
    command=root.destroy
)
exit_button.pack(pady=5)

# Run the app
root.mainloop()
