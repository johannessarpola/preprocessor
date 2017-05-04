import random
import sys


def read_write_random_lines(input_file, output_file, rows):
    """
    Random lines to new file
    """
    lines = []
    with open(input_file, "rb") as source:
        lines = [line for line in source]
    random_choice = random.sample(lines, rows)
    with open(output_file, "wb") as sink:
        sink.write(b"".join(random_choice))


# python .\scripts\random-lines.py .\.data\wiki\en\enwiki-latest-all-titles-in-ns0 .\.data\wiki\en\sample\enwiki-latest-all-titles-in-ns0-1000000.txt 1000000

if len(sys.argv) == 4:
    input_file = sys.argv[1]
    output_file = sys.argv[2]
    rows = int(sys.argv[3])
    read_write_random_lines(input_file, output_file, rows)
    print("done!")
else:
    sys.exit(1)
