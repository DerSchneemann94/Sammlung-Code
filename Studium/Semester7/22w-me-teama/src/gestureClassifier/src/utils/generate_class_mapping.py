from base_dir import BASE_DIR


def main():
    data_dir = list((BASE_DIR / "data").iterdir())

    class_map = open("class_mapping.py", "w")
    class_map.write("# auto generated class to name mapping file\n\n")
    class_map.write("GESTURE_MAP = {\n")

    count = 0
    for gesture_dir in sorted(data_dir):
        if gesture_dir.is_dir():
            class_map.write(f"\t{count}: \"{gesture_dir.name}\",\n")
            count += 1
    class_map.write("}\n")
    class_map.write(f"NC = {count}\n")
    class_map.close()


if __name__ == '__main__':
    main()
