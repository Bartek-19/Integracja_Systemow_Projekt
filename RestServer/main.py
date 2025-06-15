import mysql.connector
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator
import os

base_dir = os.path.dirname(os.path.abspath(__file__))

try:
    # Połączenie z bazą danych
    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="bazaprojekt"
    )
    mycursor = mydb.cursor()

    # Pobranie danych z tabeli graduates
    mycursor.execute("SELECT * FROM graduates")
    graduates_data = mycursor.fetchall()
    years_graduates = []
    values_graduates = []

    for year, value in graduates_data:
        years_graduates.append(year)
        values_graduates.append(value)

    # Pobranie danych z tabeli inflation
    mycursor.execute("SELECT * FROM inflation")
    inflation_data = mycursor.fetchall()
    years_inflation = []
    values_inflation = []

    for year, value in inflation_data:
        years_inflation.append(year)
        values_inflation.append(value)

    ### WYKRES LINIOWY ###
    fig1, ax1 = plt.subplots(figsize=(10, 6))
    ax1.plot(years_graduates, values_graduates, color='blue', marker='o', label='Absolwenci')
    ax1.set_xlabel("Rok")
    ax1.set_ylabel("Liczba absolwentów", color='blue')
    ax1.tick_params(axis='y', labelcolor='blue')
    ax1.xaxis.set_major_locator(MaxNLocator(integer=True))

    ax2 = ax1.twinx()
    ax2.plot(years_inflation, values_inflation, color='red', marker='s', label='Inflacja')
    ax2.set_ylabel("Wskaźnik inflacji (%)", color='red')
    ax2.tick_params(axis='y', labelcolor='red')

    ax1.set_title("Wykres liniowy: Absolwenci vs Inflacja")
    ax1.grid(True)
    lines1, labels1 = ax1.get_legend_handles_labels()
    lines2, labels2 = ax2.get_legend_handles_labels()
    ax1.legend(lines1 + lines2, labels1 + labels2, loc='upper left')
    plt.tight_layout()
    plt.savefig(os.path.join(base_dir, "wykres_liniowy.png"))
    plt.close()

    ### WYKRES SŁUPKOWY ###
    fig2, ax3 = plt.subplots(figsize=(10, 6))
    width = 0.4

    # Dostosowanie pozycji słupków, by nie nachodziły
    years_set = sorted(set(years_graduates) | set(years_inflation))
    x_indexes = list(range(len(years_set)))
    year_to_index = {year: idx for idx, year in enumerate(years_set)}

    grads_x = [year_to_index[year] for year in years_graduates]
    infl_x = [year_to_index[year] + width for year in years_inflation]

    ax3.bar(grads_x, values_graduates, width=width, color='blue', label='Absolwenci')
    ax4 = ax3.twinx()
    ax4.bar(infl_x, values_inflation, width=width, color='red', label='Inflacja')

    ax3.set_xlabel("Rok")
    ax3.set_ylabel("Liczba absolwentów", color='blue')
    ax4.set_ylabel("Wskaźnik inflacji (%)", color='red')
    ax3.set_title("Wykres słupkowy: Absolwenci vs Inflacja")

    ax3.set_xticks([i + width / 2 for i in x_indexes])
    ax3.set_xticklabels(years_set)
    ax3.xaxis.set_major_locator(MaxNLocator(integer=True))

    # Legendy
    lines1, labels1 = ax3.get_legend_handles_labels()
    lines2, labels2 = ax4.get_legend_handles_labels()
    ax3.legend(lines1 + lines2, labels1 + labels2, loc='upper left')

    plt.tight_layout()
    plt.savefig(os.path.join(base_dir, "wykres_slupkowy.png"))
    plt.close()

    print("Wykresy zapisane jako 'wykres_liniowy.png' i 'wykres_slupkowy.png'.")

except mysql.connector.Error as err:
    print(f"Błąd: {err}")

finally:
    if 'mycursor' in locals():
        mycursor.close()
    if 'mydb' in locals():
        mydb.close()
