package sudokusolver.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Service.SudokuService;

import java.util.Map;

@Controller
public class SudokuController {

    @Autowired
    private SudokuService sudokuService;

    @GetMapping("/")
    public String index() {
        return "views";
    }

    @PostMapping("/")
    public String solve(@RequestParam Map<String, String> params, Model model) {
        SudokuBoard board = new SudokuBoard();
        int[][] originalBoard = new int[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String value = params.get("cell_" + r + "_" + c);
                int num = (value == null || value.isEmpty()) ? 0 : Integer.parseInt(value);
                board.setCells(r, c, num);
                originalBoard[r][c] = num; // Lưu lại bảng gốc trước khi giải
            }
        }

        board = sudokuService.solve(board);

        model.addAttribute("board", board.getCells());
        model.addAttribute("originalBoard", originalBoard); // Gửi bảng gốc sang view
        return "views";
    }
}
